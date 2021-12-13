import React, { useState, useEffect } from 'react';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import CircularProgress from '@mui/material/CircularProgress';

import { get } from '../../../util/session';

import { getInfoStudent } from '../service';
import { getPesByProximityCompletion } from './service';

import { css } from '../styles';

const RecommendationPes = () => {

    const [loading, setLoading] = useState(true);
    const [isExist, setIsExist] = useState(false);

    const [data, setData] = useState({
        nome: '',
        cargaHorariaObrigatoria: 0,
        cargaHorariaObrigatoriaPendente: 0,
        cargaHorariaOptativaMinima: 0,
        cargaHorariaOptativaPendente: 0,
        disciplinasObrigatorias: [],
        disciplinasOptativas: [],
    });

    useEffect(() => {
        getInfoStudent(get('user').id)
            .then(response => {
                getPesByProximityCompletion(response.data['id-vinculos'][0])
                    .then(response => {
                        setData(response.data);
                    })
                    .catch(error => {
                        console.warn(error);
                    })
                    .finally(() => {
                        setLoading(false);
                    });
            })
            .catch(error => {
                console.warn(error);
            });
    }, []);

    useEffect(() => {
        if (data.nome) {
            setIsExist(true);
        } else {
            setIsExist(false);
        }
    }, [data]);

    if (loading === false) {
        if (isExist) {
            return (
                <Grid container={true} spacing={3}>
                    <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography variant="h5" component="div" gutterBottom={true} sx={css.title}>
                            {data.nome}
                        </Typography>
                        <Typography paragraph={true} sx={css.info}>
                            Carga Horária Obrigatória: {data.cargaHorariaObrigatoria - data.cargaHorariaObrigatoriaPendente} de {data.cargaHorariaObrigatoria}
                        </Typography>
                        <Typography paragraph={true} sx={css.info}>
                            Carga Horária Optativa Mnínima: {data.cargaHorariaOptativaMinima - data.cargaHorariaOptativaPendente} de {data.cargaHorariaOptativaMinima}
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
                                        {data.disciplinasObrigatorias.length > 0 ? data.disciplinasObrigatorias.map((item, index) =>
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
                                        {data.disciplinasOptativas.length > 0 ? data.disciplinasOptativas.map((item, index) =>
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
                <Grid container={true} spacing={3} sx={css.container}>
                    <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography paragraph={true} gutterBottom={true} sx={css.info}>
                            Devido a falta de cumprimento de disciplinas relacionadas aos programas de estudos secundários existentes, não há recomendações.
                        </Typography>
                    </Grid>
                </Grid>
            );
        }
    } else {
        return (
            <Grid
                container={true}
                direction="row"
                justifyContent="center"
                alignItems="center"
                sx={css.container}
            >
                <CircularProgress />
            </Grid>
        );
    }
};

export default RecommendationPes;