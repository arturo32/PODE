import React, { useEffect, useState } from 'react';

import { ThemeProvider } from '@mui/material/styles';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import Button from '@mui/material/Button';

import { form } from '../../component/theme';
import TransferList from '../../component/transferList';

import { css } from './styles';
import {
    adicionaDisciplinasCursadas, adicionaDisciplinasPendentes,
    getInfoVinculos,
    listDisciplinasObrigatoriasCurso,
    listDisciplinasObrigatoriasEnfase,
    listDisciplinasObrigatoriasPes,
    listDisciplinasOptativasEnfase,
    listDisciplinasOptativasPes,
    listDisciplinasOptivativasCurso,
    listEnfase,
    listPes
} from "./service";
import {getInfoStudent} from "../recommendations/service";
import {get} from "../../util/session";
import {getPesByProximityCompletion} from "../recommendations/pes/service";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import {Link} from "react-router-dom";
import Dialog from "@mui/material/Dialog";


const CoursePlan = () => {

    const [enfases, setEnfases] = useState([]);
    const [pes, setPes] = useState([]);

    const [enfaseSelecionada, setEnfaseSelecionada] = useState(null);
    const [pesSelecionados, setPesSelecionados] = useState([]);

    const [cursoObrigatorias, setCursoObrigatorias] = useState([]);
    const [cursoOptativas, setCursoOptativas] = useState([]);
    const [cursoObrigatoriasPagas, setCursoObrigatoriasPagas] = useState([]);
    const [cursoOptativasPagas, setCursoOptativasPagas] = useState([]);

    const [enfaseObrigatorias, setEnfaseObrigatorias] = useState([]);
    const [enfaseObrigatoriasPagas, setEnfaseObrigatoriasPagas] = useState([]);
    const [enfaseOptativas, setEnfaseOptativas] = useState([]);
    const [enfaseOptativasPagas, setEnfaseOptativasPagas] = useState([]);

    const [pesObrigatorias, setPesObrigatorias] = useState([]);
    const [pesOptativas, setPesOptativas] = useState([]);
    const [pesObrigatoriasPagas, setPesObrigatoriasPagas] = useState([]);
    const [pesOptativasPagas, setPesOptativasPagas] = useState([]);

    const [idCurso, setIdCurso] = useState(null);

    const [estudanteInfo, setEstudanteInfo] = useState(null);

    const [openConfirmacao, setOpenConfirmacao] = useState(false);

    const submit = () => {
        let disciplinasCursada = [
            ...cursoObrigatoriasPagas,
            ...cursoOptativasPagas,
            ...enfaseObrigatoriasPagas,
            ...enfaseOptativasPagas,
            ...pesObrigatoriasPagas,
            ...pesOptativasPagas
        ];

        /*let disciplinasPendentes = [
            ...cursoObrigatorias,
            ...enfaseObrigatorias,
            ...pesObrigatorias,
        ];*/


        disciplinasCursada = disciplinasCursada.map(e => {
            return {'id-disciplina': e.id, 'periodo': (e.periodo === undefined? estudanteInfo.periodoAtualPeriodo : e.periodo)}
        });
        /*disciplinasPendentes = disciplinasPendentes.map(e => {
            return {'id-disciplina': e.id, 'periodo': (e.periodo === undefined? estudanteInfo.periodoAtualPeriodo : e.periodo)}
        });*/
        adicionaDisciplinasCursadas(disciplinasCursada, estudanteInfo.idPlanoCurso)
            .then(response => {
                console.log(response)
                /*adicionaDisciplinasPendentes(disciplinasPendentes, estudanteInfo.idPlanoCurso)
                        .then(response => {
                            console.log(response)
                        })
                        .catch(erro => {
                            console.warn(erro)
                        })
                        .finally(() => {
                            setOpenConfirmacao(true);
                        });*/
            })
            .catch(erro =>{
                console.warn(erro)
            })
            .finally(() => {
                setOpenConfirmacao(true);
            });


    };

    const params = {
        params: {
            page: 0,
            limit: 1000
        }
    };

    useEffect(() => {
        getInfoStudent(get('user').id)
            .then(response => {
                getInfoVinculos(params, response.data['id-vinculos'][0])
                    .then(responseVinculo => {
                        if(responseVinculo.status === 200){
                            console.log(responseVinculo.data)
                            setEstudanteInfo(responseVinculo.data);
                            setIdCurso(responseVinculo.data.idCurso);
                        }
                    });
            })
            .catch(error => {
                console.warn(error);
            });
    }, []);




    //Ênfases por curso do estudante
    useEffect(() => {
        if(idCurso !== null){
            listEnfase(params, idCurso)
                .then(response => {
                    if(response.status === 200){
                        setEnfases(response.data);
                    }
                });
        }
    }, [idCurso]);

    //PES
    useEffect(() => {
        if(idCurso !== null) {
            listPes(params, idCurso)
                .then(response => {
                    if (response.status === 200) {
                        setPes(response.data);
                    }
                });
        }
    }, [idCurso]);

    //Disciplinas das ênfases
    useEffect(() => {
        if(enfaseSelecionada !== null){
            listDisciplinasObrigatoriasEnfase(params, enfaseSelecionada.id)
                .then(response => {
                    if(response.status === 200){
                        setEnfaseObrigatorias(response.data);
                    }
                });
        }
    }, [enfaseSelecionada]);
    useEffect(() => {
        if(enfaseSelecionada !== null){
            listDisciplinasOptativasEnfase(params, enfaseSelecionada.id)
                .then(response => {
                    if(response.status === 200){
                        setEnfaseOptativas(response.data);
                    }
                });
        }
    }, [enfaseSelecionada]);


    //Disciplinas das PES
    useEffect(() => {
        setPesObrigatorias([]);
        for(let pesSelecionado of pesSelecionados){
            listDisciplinasObrigatoriasPes(params, pesSelecionado.id)
                .then(response => {
                    if(response.status === 200){
                        setPesObrigatorias(pesObrigatorias.concat(response.data));
                    }
                });
        }
    }, [pesSelecionados]);
    useEffect(() => {
        setPesOptativas([]);
        for(let pesSelecionado of pesSelecionados){
            listDisciplinasOptativasPes(params, pesSelecionado.id)
                .then(response => {
                    if(response.status === 200){
                        setPesOptativas(pesOptativas.concat(response.data));
                    }
                });
        }
    }, [pesSelecionados]);


    //Disciplinas do curso
    useEffect(() => {
        if(idCurso !== null) {
            listDisciplinasObrigatoriasCurso({params: {page: 0, limit: 1000}}, idCurso)
                .then(response => {
                    if (response.status === 200) {
                        setCursoObrigatorias(response.data.map(e => {
                            return {...e, tipo: 'CURSO_OBRIGATORIA'}
                        }));
                    }
                });
        }
    }, [idCurso]);
    useEffect(() => {
        if(idCurso !== null) {
            listDisciplinasOptivativasCurso({params: {page: 0, limit: 1000}}, idCurso)
                .then(response => {
                    if (response.status === 200) {
                        setCursoOptativas(response.data.map(e => {
                            return {...e, tipo: 'CURSO_OPTATIVA'}
                        }));
                    }
                });
        }
    }, [idCurso]);


    return (
        <ThemeProvider theme={form}>
            <Grid container={true} spacing={3}>
                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                    <Typography variant="h4" component="div" gutterBottom={true} sx={css.title}>
                        Meu plano de curso
                    </Typography>
                </Grid>
                <Grid item={true} xs={12} sm={6} md={6} lg={4} xl={3}>
                    <Autocomplete
                        options={enfases}
                        getOptionLabel={option => option.nome}
                        renderInput={(params) =>
                            <TextField {...params} label="Ênfase" placeholder="Escolha não obrigatória" InputLabelProps={{ shrink: true }} />
                        }
                        value={enfaseSelecionada}
                        onChange={(_, object) => setEnfaseSelecionada(object)}
                        disablePortal={true}
                    />
                </Grid>
                <Grid item={true} xs={12} sm={6} md={6} lg={8} xl={9}>
                    <Autocomplete
                        options={pes}
                        getOptionLabel={option => option.nome}
                        isOptionEqualToValue={(option, value) => option.id === value.id}
                        renderInput={(params) =>
                            <TextField {...params} label="Pes de interesse" placeholder="Escolha(s) não obrigatória(s)" InputLabelProps={{ shrink: true }} />
                        }
                        value={pesSelecionados}
                        onChange={(_, object) => setPesSelecionados(object)}
                        multiple={true}
                        disablePortal={true}
                    />
                </Grid>
                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                    <TransferList
                        labels={['Disciplinas existentes e não cursadas', 'Disciplinas cursadas']}
                        left={{cursoObrigatorias, cursoOptativas, enfaseObrigatorias, enfaseOptativas, pesObrigatorias, pesOptativas}}
                        right={{cursoObrigatorias: cursoObrigatoriasPagas, cursoOptativas: cursoOptativasPagas,
                            enfaseObrigatorias: enfaseObrigatoriasPagas, enfaseOptativas: enfaseOptativasPagas,
                            pesObrigatorias: pesObrigatoriasPagas, pesOptativas: pesOptativasPagas}}
                        handleChangeLeftCursoObrigatorias={event => setCursoObrigatorias(event)}
                        handleChangeRightCursoObrigatorias={event => setCursoObrigatoriasPagas(event)}
                        handleChangeLeftCursoOptativas={event => setCursoOptativas(event)}
                        handleChangeRightCursoOptativas={event => setCursoOptativasPagas(event)}

                        handleChangeLeftEnfaseObrigatorias={event => setEnfaseObrigatorias(event)}
                        handleChangeRightEnfaseObrigatorias={event => setEnfaseObrigatoriasPagas(event)}
                        handleChangeLeftEnfaseOptativas={event => setEnfaseOptativas(event)}
                        handleChangeRightEnfaseOptativas={event => setEnfaseOptativasPagas(event)}

                        handleChangeLeftPesObrigatorias={event => setPesObrigatorias(event)}
                        handleChangeRightPesObrigatorias={event => setPesObrigatoriasPagas(event)}
                        handleChangeLeftPesOptativas={event => setPesOptativas(event)}
                        handleChangeRightPesOptativas={event => setPesOptativasPagas(event)}
                    />
                </Grid>
                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                    <Button
                        variant="contained"
                        size="medium"
                        onClick={() => submit()}
                        sx={css.button}
                    >
                        Salvar
                    </Button>
                </Grid>
            </Grid>
            <Dialog open={openConfirmacao}>
                <DialogTitle>Plano de curso atualizado com sucesso!</DialogTitle>
                <DialogActions sx={css.dialogButton}>
                    <Button
                            variant="contained"
                            size="medium"
                            sx={css.button}
                    >
                        Ok!
                    </Button>
                </DialogActions>
            </Dialog>
        </ThemeProvider>
    );

};

export default CoursePlan;