import React, { useState, useEffect } from 'react';

import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import { ThemeProvider } from '@mui/material/styles';

import Page from '../../component/page';
import { form } from '../../component/theme';

import { validateEmail, validatePassword } from '../../util/validation';

import { login } from './service';

import { css } from './styles';

const Login = () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');


    const submit = () => {
        login('username=' + email +  '&password=' + password)
            .then(response => {
                if(response.status === 200){
                    window.location = '/plataforma/plano-de-curso';
                }
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
                                        Login
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
                                        type="password"
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
                                    <Button
                                        variant="contained"
                                        size="medium"
                                        disabled={!validateEmail(email) || !validatePassword(password)}
                                        onClick={() => submit()}
                                        sx={css.button}
                                    >
                                        Entrar
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

export default Login;