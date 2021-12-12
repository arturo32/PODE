import React from 'react';

import { Link } from "react-router-dom";

import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';

import { css } from './styles';

const Footer = () => {

    const github = [
        'http://lattes.cnpq.br/5738508366326563',
        'http://lattes.cnpq.br/',
        'http://lattes.cnpq.br/',
    ];

    return (
        <Box>
            <AppBar position="fixed" color="primary" sx={css.appBar}>
                <Toolbar>
                    <Grid container={true}>
                        <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Typography paragraph={true} sx={css.typography}>
                                Por <Link to={{ pathname: github[0] }} style={css.link}>Alex Sandro de Paiva</Link>, <Link to={{ pathname: github[1] }} style={css.link}>João Vitor Venceslau Coelho</Link> e <Link to={{ pathname: github[2] }} style={css.link}>Arturo Fonseca de Souza</Link>
                            </Typography>
                        </Grid>
                        <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Typography paragraph={true} sx={css.typography}>
                                &copy; Copyright 2021 - Todos os direitos reservados | Image by <Link to={{ pathname: 'https://pixabay.com/users/startupstockphotos-690514/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=593371' }} style={css.link}>StartupStockPhotos</Link> from <Link to={{ pathname: 'https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=59337' }} style={css.link}>Pixabay</Link>
                            </Typography>
                        </Grid>
                    </Grid>
                </Toolbar>
            </AppBar>
        </Box >
    );

};

export default Footer;