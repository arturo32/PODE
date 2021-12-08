import React, { useState, useEffect } from 'react';

import { ThemeProvider } from '@mui/material/styles';

import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import Button from '@mui/material/Button';

import Page from '../../component/page';
import { form } from '../../component/theme';
import MaskPeriod from '../../component/mask/period';

import { validateEmail, validatePassword, validatePeriod, validateGeneric } from '../../util/validation';

import { create } from './service';

import { css } from './styles';

const Register = () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [registration, setRegistration] = useState('');
    const [course, setCourse] = useState('BTI');
    const [firstPeriod, setFirstPeriod] = useState('');
    const [currentPeriod, setCurrentPeriod] = useState('');

    const submit = () => {
        create()
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.log(error);
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
                                        Cadastrar aluno
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
                                        value={password}
                                        onChange={event => setPassword(event.target.value)}
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
                                        value={name}
                                        onChange={event => setName(event.target.value)}
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
                                        value={registration}
                                        onChange={event => setRegistration(event.target.value)}
                                        label="Matrícula"
                                        variant="outlined"
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    />
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        select={true}
                                        value={course}
                                        onChange={event => setCourse(event.target.value)}
                                        label="Curso"
                                        variant="outlined"
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    >
                                        <MenuItem value="BTI">
                                            Bacharelado em Tecnologia da Informação
                                        </MenuItem>
                                    </TextField>
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        type="text"
                                        value={firstPeriod}
                                        onChange={event => setFirstPeriod(event.target.value)}
                                        label="Semestre de entrada"
                                        variant="outlined"
                                        InputProps={{
                                            inputComponent: MaskPeriod,
                                        }}
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    />
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <TextField
                                        type="text"
                                        value={currentPeriod}
                                        onChange={event => setCurrentPeriod(event.target.value)}
                                        label="Período atual"
                                        variant="outlined"
                                        InputProps={{
                                            inputComponent: MaskPeriod,
                                        }}
                                        InputLabelProps={{ shrink: true }}
                                        required={true}
                                        fullWidth={true}
                                    />
                                </Grid>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <Button
                                        variant="contained"
                                        size="medium"
                                        disabled={
                                            !validateEmail(email) ||
                                            !validatePassword(password) ||
                                            !validateGeneric(name) ||
                                            !validateGeneric(registration) ||
                                            !validatePeriod(firstPeriod) ||
                                            !validatePeriod(currentPeriod)
                                        }
                                        onClick={() => submit()}
                                        sx={css.button}
                                    >
                                        Concluir
                                    </Button>
                                </Grid>
                            </Grid>
                        </Box>
                    </Grid>
                </ThemeProvider>
            }
        />
    );

};

export default Register;