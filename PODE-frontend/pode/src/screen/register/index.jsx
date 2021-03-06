import React, { useState, useEffect } from 'react';

import { Link } from "react-router-dom";

import { ThemeProvider } from '@mui/material/styles';

import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import Button from '@mui/material/Button';
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import CircularProgress from '@mui/material/CircularProgress';

import Page from '../../component/page';
import { form } from '../../component/theme';
import MaskYear from '../../component/mask/year';
import MaskPeriod from '../../component/mask/period';
import Alert from '../../component/snackbar';

import { validateEmail, validatePassword, validateYear, validatePeriod, validateGeneric } from '../../util/validation';

import { createStudent, createVinculo } from './service';

import { css } from './styles';

const Register = () => {

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [nome, setNome] = useState('');
    const [matricula, setMatricula] = useState('');
    const [curso, setCurso] = useState('BTI');
    const [periodoInicialAno, setPeriodoInicialAno] = useState('');
    const [periodoInicialPeriodo, setPeriodoInicialPeriodo] = useState('');
    const [periodoAtualAno, setPeriodoAtualAno] = useState('');
    const [periodoAtualPeriodo, setPeriodoAtualPeriodo] = useState('');
    const [openConfirmacao, setOpenConfirmacao] = useState(false);
    const [error, setError] = useState({ active: false, message: '' });
    const [loading, setLoading] = useState(false);

    const submit = () => {
        setLoading(true);
        createStudent({ email, senha, nome })
            .then(response => {
                if (response.status === 200) {
                    createVinculo({
                        'idEstudante': response.data.id, 'idCurso': 1, matricula,
                        periodoInicialAno, periodoInicialPeriodo, periodoAtualAno, periodoAtualPeriodo
                    })
                        .then(response => {
                            if (response.status === 200) {
                                setOpenConfirmacao(true);
                            }
                        })
                        .catch(error => {
                            setError({ active: true, message: error.response.data.userMessage });
                        })
                        .finally(() => {
                            setLoading(false);
                        });
                }
            })
            .catch(error => {
                setLoading(false);
                setError({ active: true, message: error.response.data.userMessage });
            });
    };

    useEffect(() => {
        document.body.classList.remove('home');
    }, []);

    return (
        <Page
            component={
                <ThemeProvider theme={form}>
                    <Grid container={true} direction="row" justifyContent="center" alignItems="center">
                        <Box sx={css.root}>
                            <Grid container={true} spacing={3}>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <Typography variant="h4" component="div" align="center" gutterBottom={true} sx={css.title}>
                                        Cadastrar estudante
                                    </Typography>
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        type="email"
                                        value={email}
                                        onChange={event => setEmail(event.target.value)}
                                        label="E-mail"
                                        variant="outlined"
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    />
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        type="text"
                                        value={senha}
                                        onChange={event => setSenha(event.target.value)}
                                        label="Senha"
                                        variant="outlined"
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    />
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        type="text"
                                        value={nome}
                                        onChange={event => setNome(event.target.value)}
                                        label="Nome"
                                        variant="outlined"
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    />
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        type="text"
                                        value={matricula}
                                        onChange={event => setMatricula(event.target.value)}
                                        label="Matr??cula"
                                        variant="outlined"
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    />
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        select={true}
                                        value={curso}
                                        onChange={event => setCurso(event.target.value)}
                                        label="Curso"
                                        variant="outlined"
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    >
                                        <MenuItem value="BTI">
                                            Bacharelado em Tecnologia da Informa????o
                                        </MenuItem>
                                    </TextField>
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <Grid container={true} spacing={1}>
                                        <Grid item xs={6}>
                                            <TextField
                                                type="text"
                                                value={periodoInicialAno}
                                                onChange={event => setPeriodoInicialAno(event.target.value)}
                                                label="Per??odo de entrada (ano)"
                                                placeholder={'0000'}
                                                variant="outlined"
                                                InputProps={{
                                                    inputComponent: MaskYear,
                                                }}
                                                InputLabelProps={{ shrink: true }}
                                                required={true}
                                                fullWidth={true}
                                            />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField
                                                type="text"
                                                value={periodoInicialPeriodo}
                                                onChange={event => setPeriodoInicialPeriodo(event.target.value)}
                                                label="Per??odo de entrada (semestre)"
                                                placeholder={'0'}
                                                variant="outlined"
                                                InputProps={{
                                                    inputComponent: MaskPeriod,
                                                }}
                                                InputLabelProps={{ shrink: true }}
                                                required={true}
                                                fullWidth={true}
                                            />
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <Grid container={true} spacing={1}>
                                        <Grid item xs={6}>
                                            <TextField
                                                type="text"
                                                value={periodoAtualAno}
                                                onChange={event => setPeriodoAtualAno(event.target.value)}
                                                label="Per??odo atual (ano)"
                                                placeholder={'0000'}
                                                variant="outlined"
                                                InputProps={{
                                                    inputComponent: MaskYear,
                                                }}
                                                InputLabelProps={{ shrink: true }}
                                                required={true}
                                                fullWidth={true}
                                            />
                                        </Grid>
                                        <Grid item xs={6}>
                                            <TextField
                                                type="text"
                                                value={periodoAtualPeriodo}
                                                onChange={event => setPeriodoAtualPeriodo(event.target.value)}
                                                label="Per??odo atual (semestre)"
                                                placeholder={'0'}
                                                variant="outlined"
                                                InputProps={{
                                                    inputComponent: MaskPeriod,
                                                }}
                                                InputLabelProps={{ shrink: true }}
                                                required={true}
                                                fullWidth={true}
                                            />
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    {loading ?
                                        <Grid
                                            container={true}
                                            direction="row"
                                            justifyContent="flex-end"
                                            alignItems="center"
                                        >
                                            <CircularProgress />
                                        </Grid> :
                                        <Button
                                            variant="contained"
                                            size="medium"
                                            disabled={
                                                !validateEmail(email) ||
                                                !validatePassword(senha) ||
                                                !validateGeneric(nome) ||
                                                !validateGeneric(matricula) ||
                                                !validateYear(periodoInicialAno) ||
                                                !validatePeriod(periodoInicialPeriodo) ||
                                                !validateYear(periodoAtualAno) ||
                                                !validatePeriod(periodoAtualPeriodo) ||
                                                periodoInicialAno > periodoAtualAno
                                            }
                                            onClick={() => submit()}
                                            sx={css.button}
                                        >
                                            Concluir
                                        </Button>
                                    }
                                </Grid>
                            </Grid>
                        </Box>
                    </Grid>
                    <Alert
                        open={error.active}
                        type="error"
                        message={error.message}
                        close={() => setError({ active: false, message: '' })}
                    />
                    <Dialog open={openConfirmacao}>
                        <DialogTitle>Cadastro realizado com sucesso!</DialogTitle>
                        <DialogActions sx={css.dialogButton}>
                            <Button
                                variant="contained"
                                size="medium"
                                component={Link}
                                to={'/entrar'}
                                sx={css.button}
                            >
                                Ok!
                            </Button>
                        </DialogActions>
                    </Dialog>
                </ThemeProvider>
            }
        />
    );

};

export default Register;