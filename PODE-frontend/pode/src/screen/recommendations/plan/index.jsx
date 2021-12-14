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
import { getSubjectsCoursePlan } from './service';

import { css } from '../styles';

const RecommendationPlan = () => {

    const [loading, setLoading] = useState(true);
    const [isExist, setIsExist] = useState(false);

    const [data, setData] = useState([]);

    useEffect(() => {
        getInfoStudent(get('user').id)
            .then(response => {
                getSubjectsCoursePlan(response.data['id-vinculos'][0])
                    .then(response => {
                        let array = [];
                        response.data.forEach(item => {
                            let index = array.findIndex(element => element[0] === item.periodo);
                            if (index !== -1) {
                                array[index][1].push(item);
                            } else {
                                array.push([item.periodo, [item]]);
                            }
                        });
                        setData(array);
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
        if (data.length) {
            setIsExist(true);
        } else {
            setIsExist(false);
        }
    }, [data]);

    if (loading === false) {
        if (isExist) {
            return (
                <Grid container={true} spacing={3}>
                    {data.map((item, index) =>
                        <Grid key={index} item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Typography variant="h6" component="div" gutterBottom={true} sx={css.title}>
                                {item[0]}º Semestre
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
                                            {item[1].length > 0 ? item[1].map((element, position) =>
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
        } else {
            return (
                <Grid container={true} spacing={3} sx={css.container}>
                    <Grid item={true} xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography paragraph={true} gutterBottom={true} sx={css.info}>
                            Tudo cumprido &#128525;
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

export default RecommendationPlan;