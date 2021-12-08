import React, { useState, useEffect } from 'react';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';

import { css } from '../styles';

const RecommendationPlan = () => {

    const [data, setData] = useState({
        periodos: [[], []],
    });

    useEffect(() => {

    }, []);

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

};

export default RecommendationPlan;