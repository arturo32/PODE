import React, { useEffect } from 'react';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';

import Page from '../../component/page';

import { css } from './styles';

const Home = () => {

    useEffect(() => {
        document.body.classList.add('home');
    }, []);

    return (
        <Page
            component={
                <Grid container={true} spacing={3}>
                    <Grid item={true} xs={12} sm={12} md={12} lg={6} xl={6}>
                        <Typography paragraph={true} sx={css.paragraph}>
                            Bem vindo(a) à Plataforma de Organização de Disciplinas do Estudante - PODE. Essa tem como objetivo auxiliar o aluno de Tecnologia da Informação da Universidade Federal do Rio Grande do Norte - UFRN na construção de um planejamento de disciplinas a serem cursadas, seja considerando a grade curricular padrão, ênfase (Desenvolvimento de Software ou Ciencia da Computação) e Programa de Estudos Secundários do Instituto Metrópole Digital (PES/IMD).
                        </Typography>
                    </Grid>
                </Grid>
            }
        />
    );

};

export default Home;