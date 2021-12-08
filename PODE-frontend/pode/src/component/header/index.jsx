import React from 'react';

import { Link } from "react-router-dom";

import { ThemeProvider } from '@mui/material/styles';

import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';

import { generic } from '../theme';

import { css } from './styles';

const Header = () => {
    return (
        <Box sx={css.bar}>
            <ThemeProvider theme={generic}>
                <AppBar position="static" color="primary">
                    <Toolbar>
                        <Typography variant="h6" component="div" sx={css.title}>
                            Plataforma de Organização de Disciplinas do Estudante
                        </Typography>
                        <Button component={Link} to="/entrar" color="inherit" sx={css.login}>ENTRAR</Button>
                        <Button component={Link} to="/cadastrar" color="inherit" sx={css.register}>CADASTRAR</Button>
                    </Toolbar>
                </AppBar>
            </ThemeProvider>
        </Box>
    );
};

export default Header;