import React, { useState, useEffect } from 'react';

import { ThemeProvider } from '@mui/material/styles';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import Box from '@mui/material/Box';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';

import FavoriteIcon from '@mui/icons-material/Favorite';
import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';

import { generic } from '../../component/theme';

import { css } from './styles';

const Recommendations = () => {

    const [option, setOption] = useState(0);
    const [data, setData] = useState({
        periodos: [],
    });

    const render = () => {
        if (option === 1) {
            return (
                <Grid container={true} spacing={3}>
                    <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography variant="h5" component="div" gutterBottom={true} sx={css.title}>
                            Recomendação: CIÊNCIAS DE DADOS
                        </Typography>
                        <Typography paragraph={true} sx={css.info}>
                            Carga Horária Mínima: 0 de 0
                        </Typography>
                        <Typography paragraph={true} sx={css.info}>
                            Carga Horária Obrigatória: 0 de 0
                        </Typography>
                        <Typography paragraph={true} sx={css.info}>
                            Carga Horária Optativa Mnínima: 0 de 0
                        </Typography>
                    </Grid>
                    <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography variant="h6" component="div" gutterBottom={true} sx={css.title}>
                            Disciplinas obrigatórias pendentes
                        </Typography>
                        <Grid container={true} spacing={3}>
                            <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                <Table size="small" aria-label="a dense table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Código</TableCell>
                                            <TableCell>Nome</TableCell>
                                            <TableCell>Carga Horária</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {data.disciplinasObrigatorias.length > 0 ? data.map((item, index) =>
                                            <TableRow key={index}>
                                                <TableCell component="th" scope="row">
                                                    {item.codigo}
                                                </TableCell>
                                                <TableCell component="th" scope="row">
                                                    {item.nome}
                                                </TableCell>
                                                <TableCell component="th" scope="row">
                                                    {item.ch}
                                                </TableCell>
                                            </TableRow>
                                        ) : <TableRow>
                                            <TableCell component="th" scope="row" align="center" colSpan={3}>
                                                Tudo cumprido &#128525;
                                            </TableCell>
                                        </TableRow>}
                                    </TableBody>
                                </Table>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography variant="h6" component="div" gutterBottom={true} sx={css.title}>
                            Disciplinas optativas possíveis e não cursadas
                        </Typography>
                        <Grid container={true} spacing={3}>
                            <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                <Table size="small" aria-label="a dense table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Código</TableCell>
                                            <TableCell>Nome</TableCell>
                                            <TableCell>Carga Horária</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {data.disciplinasOptativas.length > 0 ? data.map((item, index) =>
                                            <TableRow key={index}>
                                                <TableCell component="th" scope="row">
                                                    {item.codigo}
                                                </TableCell>
                                                <TableCell component="th" scope="row">
                                                    {item.nome}
                                                </TableCell>
                                                <TableCell component="th" scope="row">
                                                    {item.ch}
                                                </TableCell>
                                            </TableRow>
                                        ) : <TableRow>
                                            <TableCell component="th" scope="row" align="center" colSpan={3}>
                                                Você já cumpriu a carga horária miníma de disciplinas optativas &#128526;
                                            </TableCell>
                                        </TableRow>}
                                    </TableBody>
                                </Table>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            );
        } else {
            return (
                <Grid container={true} spacing={3}>
                    <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography paragraph={true} sx={css.info}>
                            Carga Horária Obrigatória: 0 de 0
                        </Typography>
                        <Typography paragraph={true} sx={css.info}>
                            Carga Horária Optativa: 0 de 0
                        </Typography>
                    </Grid>
                    {data.periodos.map((item, index) =>
                        <Grid key={index} item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Typography variant="h6" component="div" gutterBottom={true} sx={css.title}>
                                {index + 1}º Semestre
                            </Typography>
                            <Grid container={true} spacing={3}>
                                <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <Table size="small" aria-label="a dense table">
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Código</TableCell>
                                                <TableCell>Nome</TableCell>
                                                <TableCell>Carga Horária</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {item.length > 0 ? item.map((element, position) =>
                                                <TableRow key={position}>
                                                    <TableCell component="th" scope="row">
                                                        {element.codigo}
                                                    </TableCell>
                                                    <TableCell component="th" scope="row">
                                                        {element.nome}
                                                    </TableCell>
                                                    <TableCell component="th" scope="row">
                                                        {element.ch}
                                                    </TableCell>
                                                </TableRow>
                                            ) : <TableRow>
                                                <TableCell component="th" scope="row" align="center" colSpan={3}>
                                                    Nenhuma disciplina cursada
                                                </TableCell>
                                            </TableRow>}
                                        </TableBody>
                                    </Table>
                                </Grid>
                            </Grid>
                        </Grid>
                    )}
                </Grid>
            );
        }
    };

    useEffect(() => {
        // PES
        // setData({
        //     disciplinasObrigatorias: [],
        //     disciplinasOptativas: [],
        // });
        // Plano de curso
        setData({
            periodos: [[], []],
        });
    }, []);

    return (
        <ThemeProvider theme={generic}>
            <Box>
                <BottomNavigation
                    showLabels={true}
                    value={option}
                    onChange={(_, newValue) => {
                        setOption(newValue);
                    }}
                >
                    <BottomNavigationAction label="Plano de curso" icon={<FavoriteIcon />} sx={css.option} />
                    <BottomNavigationAction label="Proximidade de conclusão de um PES" icon={<SentimentVerySatisfiedIcon />} />
                </BottomNavigation>
                {render()}
            </Box>
        </ThemeProvider>
    );

};

export default Recommendations;