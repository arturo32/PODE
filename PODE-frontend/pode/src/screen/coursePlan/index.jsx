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
import {listDisciplinasObrigatoriasCurso, listDisciplinasOptivativasCurso, listEnfase, listPes} from "./service";


const CoursePlan = () => {

    const [emphasis, setEmphasis] = useState(null);
    
    const [pes, setPes] = useState([]);
    
    const [cursoObrigatorias, setCursoObrigatorias] = useState([]);
    const [cursoOptativas, setCursoOptativas] = useState([]);
    const [cursoObrigatoriasPagas, setCursoObrigatoriasPagas] = useState([]);
    const [cursoOptativasPagas, setCursoOptativasPagas] = useState([]);

    const [enfaseObrigatorias, setEnfaseObrigatorias] = useState([]);
    const [enfaseObrigatoriasPagas, setEnfaseObrigatoriasPagas] = useState([]);
    const [enfaseOptativas, setEnfaseOptativas] = useState([]);
    const [enfaseOptativasPagas, setEnfaseOptativasPagas] = useState([]);

    const [pesObrigatorias, setPesObrigatorias] = useState([]);
    const [pesObrigatoriasPagas, setPesObrigatoriasPagas] = useState([]);

    const submit = () => {
        console.log('Submit');
    };

    useEffect(() => {
        listEnfase({params: {page: 0, limit: 1000}}, 2)
                .then(response => {
                    if(response.status === 200){
                        setEnfaseObrigatorias(response.data);
                        setEnfaseOptativas([]);
                    }
                });
    }, []);

    useEffect(() => {
        listPes({params: {page: 0, limit: 1000}})
                .then(response => {
                    if(response.status === 200){
                        setPesObrigatorias(response.data);
                    }
                });
    }, []);

    useEffect(() => {
        listDisciplinasObrigatoriasCurso({params: {page: 0, limit: 1000}}, 2)
                .then(response => {
                    if(response.status === 200){
                        console.log('Obrigatórias: ', response)
                        setCursoObrigatorias(response.data.map(e => {
                            return {...e, tipo: 'CURSO_OBRIGATORIA'}
                        }));
                    }
                });
    }, []);

    useEffect(() => {
        listDisciplinasOptivativasCurso({params: {page: 0, limit: 1000}}, 2)
                .then(response => {
                    if(response.status === 200){
                        console.log('Optativas: ', response)
                        setCursoOptativas(response.data.map(e =>{
                            return {...e, tipo: 'CURSO_OPTATIVA'}
                        }));
                    }
                });
    }, []);


    useEffect(() => {
        setCursoObrigatoriasPagas([]);
    }, []);



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
                        options={enfaseObrigatorias.map(e => { return {label: e.nome, value: e.nome}})}
                        renderInput={(params) =>
                            <TextField {...params} label="Ênfase" placeholder="Escolha não obrigatória" InputLabelProps={{ shrink: true }} />
                        }
                        value={emphasis}
                        onChange={(_, object) => setEmphasis(object)}
                        disablePortal={true}
                    />
                </Grid>
                <Grid item={true} xs={12} sm={6} md={6} lg={8} xl={9}>
                    <Autocomplete
                        options={pesObrigatorias.map(e => { return {label: e.nome, value: e.nome}})}
                        renderInput={(params) =>
                            <TextField {...params} label="Pes de interesse" placeholder="Escolha(s) não obrigatória(s)" InputLabelProps={{ shrink: true }} />
                        }
                        value={pes}
                        onChange={(_, object) => setPes(object)}
                        multiple={true}
                        disablePortal={true}
                    />
                </Grid>
                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                    <TransferList
                        labels={['Disciplinas existentes e não cursadas', 'Disciplinas cursadas']}
                        left={{cursoObrigatorias, cursoOptativas, enfaseObrigatorias, enfaseOptativas, pesObrigatorias}}
                        right={{cursoObrigatorias: cursoObrigatoriasPagas, cursoOptativas: cursoOptativasPagas,
                            enfaseObrigatorias: enfaseObrigatoriasPagas, enfaseOptativas: enfaseOptativasPagas,
                            pesObrigatorias: pesObrigatoriasPagas}}
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
        </ThemeProvider>
    );

};

export default CoursePlan;