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
import {listEnfase, listPes} from "./service";

const CoursePlan = () => {

    const [emphasis, setEmphasis] = useState(null);
    const [emphasisList, setEmphasisList] = useState([]);
    const [pes, setPes] = useState([]);
    const [pesList, setPesList] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [subjectsAttended, setSubjectsAttended] = useState([]);

    const submit = () => {
        console.log('Submit');
    };

    useEffect(() => {
        listEnfase({params: {page: 0, limit: 1000}})
                .then(response => {
                    if(response.status === 200){
                        setEmphasisList(response.data)
                    }
                });
    }, []);

    useEffect(() => {
        listPes({params: {page: 0, limit: 1000}})
                .then(response => {
                    if(response.status === 200){
                        setPesList(response.data)
                    }
                });
    }, []);

    useEffect(() => {
        setSubjects(['IMD0028 - Fundamentos da Matemática Computacional I']);
        setSubjectsAttended([]);
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
                        options={emphasisList.map(e => { return {label: e.nome, value: e.nome}})}
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
                        options={pesList.map(e => { return {label: e.nome, value: e.nome}})}
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
                        left={subjects}
                        right={subjectsAttended}
                        handleChangeLeft={event => setSubjects(event)}
                        handleChangeRight={event => setSubjectsAttended(event)}
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