import React, { useState, useEffect } from 'react';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';

import { css } from '../styles';

const RecommendationPes = () => {

    const [data, setData] = useState({
        disciplinasObrigatorias: [],
        disciplinasOptativas: [],
    });

    useEffect(() => {

    }, []);

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
};

export default RecommendationPes;