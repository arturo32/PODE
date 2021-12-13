import React, { useState, useEffect } from 'react';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';

import { css } from './styles';
import {Collapse, ListItemButton, ListSubheader} from "@mui/material";
import {ExpandLess, ExpandMore} from "@mui/icons-material";

const not = (a, b) => {
    return a.filter((value) => b.indexOf(value) === -1);
};

const intersection = (a, b) => {
    return a.filter((value) => b.indexOf(value) !== -1);
};

const TransferList = (props) => {

    const { labels, left, right, handleChangeLeft, handleChangeRight } = props;

    const [checked, setChecked] = useState([]);

    const [openNaoCursadasCurso, setOpenNaoCursadasCurso] = useState(true);
    const [openNaoCursadasCursoObrigatorias, setOpenNaoCursadasCursoObrigatorias] = useState(true);
    const [openNaoCursadasCursoOptativas, setOpenNaoCursadasCursoOptativas] = useState(true);

    const [openNaoCursadasEnfase, setOpenNaoCursadasEnfase] = useState(true);
    const [openNaoCursadasEnfaseObrigatorias, setOpenNaoCursadasEnfaseObrigatorias] = useState(true);
    const [openNaoCursadasEnfaseOptativas, setOpenNaoCursadasEnfaseOptativas] = useState(true);

    const [openNaoCursadasPes, setOpenNaoCursadasPes] = useState(true);


    const leftCheckedCursoObrigatorias = intersection(checked, left.cursoObrigatorias);
    const leftCheckedCursoOptativas = intersection(checked, left.cursoOptativas);
    const rightChecked = intersection(checked, right);


    const handleClickNaoCursadasCurso = () => {
        setOpenNaoCursadasCurso(!openNaoCursadasCurso);
    };
    const handleClickNaoCursadasCursoObrigatorias = () => {
        setOpenNaoCursadasCursoObrigatorias(!openNaoCursadasCursoObrigatorias);
    };
    const handleClickNaoCursadasCursoOptativas = () => {
        setOpenNaoCursadasCursoOptativas(!openNaoCursadasCursoOptativas);
    };

    const handleClickNaoCursadasEnfase = () => {
        setOpenNaoCursadasEnfase(!openNaoCursadasEnfase);
    };
    const handleClickNaoCursadasEnfaseObrigatorias = () => {
        setOpenNaoCursadasEnfaseObrigatorias(!openNaoCursadasEnfaseObrigatorias);
    };
    const handleClickNaoCursadasEnfaseOptativas = () => {
        setOpenNaoCursadasEnfaseOptativas(!openNaoCursadasEnfaseOptativas);
    };

    const handleClickNaoCursadasPes = () => {
        setOpenNaoCursadasPes(!openNaoCursadasPes);
    };



    const handleToggle = (value) => () => {
        const currentIndex = checked.indexOf(value);
        const newChecked = [...checked];
        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }
        setChecked(newChecked);
    };

    const handleAllRight = () => {
        handleChangeRight(right.concat(left.cursoObrigatorias));
        handleChangeLeft([]);
    };

    const handleCheckedRight = () => {
        handleChangeRight(right.concat(leftCheckedCursoObrigatorias));
        handleChangeLeft(not(left.cursoObrigatorias, leftCheckedCursoObrigatorias));
        setChecked(not(checked, leftCheckedCursoObrigatorias));
    };

    const handleCheckedLeft = () => {
        handleChangeLeft(left.cursoObrigatorias.concat(rightChecked));
        handleChangeRight(not(right, rightChecked));
        setChecked(not(checked, rightChecked));
    };

    const handleAllLeft = () => {
        handleChangeLeft(left.cursoObrigatorias.concat(right));
        handleChangeRight([]);
    };

    const listDisciplinas = (disciplinas) => (
        <List component="div" disablePadding >
            {disciplinas.map((value) => {
                const labelId = `transfer-list-item-${value}-label`;
                return (
                        <ListItem
                                key={value}
                                role="listitem"
                                button={true}
                                onClick={handleToggle(value)}
                                sx={{ pl: 8 }}
                        >
                            <ListItemIcon>
                                <Checkbox
                                        checked={checked.indexOf(value) !== -1}
                                        tabIndex={-1}
                                        disableRipple
                                        inputProps={{
                                            'aria-labelledby': labelId,
                                        }}
                                />
                            </ListItemIcon>
                            <ListItemText id={labelId} primary={value} />
                        </ListItem>
                );
            })}
        </List>
    )

    const customSubList = (items, label, handleFunction, openState) => (
        <div>
            <ListItemButton onClick={handleFunction} sx={{ pl: 4 }}>
                <ListItemText  primary={label} />
                {openState ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse in={openState} timeout="auto" unmountOnExit>
                {listDisciplinas(items)}
            </Collapse>
        </div>
    );

    const customList = (items, label) => (
        <Paper sx={css.paper}>
            <Typography paragraph={true} gutterBottom={true} sx={css.title}>
                {label}
            </Typography>
            <List dense component="div" role="list">
                <ListItemButton onClick={handleClickNaoCursadasCurso}>
                    <ListItemText  primary={'Curso'} />
                    {openNaoCursadasCurso ? <ExpandLess /> : <ExpandMore />}
                </ListItemButton>
                <Collapse in={openNaoCursadasCurso} timeout="auto" unmountOnExit>
                    {customSubList(items, 'Obrigatórias', handleClickNaoCursadasCursoObrigatorias, openNaoCursadasCursoObrigatorias)}
                    {customSubList([], 'Optativas', handleClickNaoCursadasCursoOptativas, openNaoCursadasCursoOptativas)}
                </Collapse>

                <ListItemButton onClick={handleClickNaoCursadasEnfase}>
                    <ListItemText  primary={'Ênfase'} />
                    {openNaoCursadasEnfase ? <ExpandLess /> : <ExpandMore />}
                </ListItemButton>
                <Collapse in={openNaoCursadasEnfase} timeout="auto" unmountOnExit>
                    {customSubList([], 'Obrigatórias', handleClickNaoCursadasEnfaseObrigatorias, openNaoCursadasEnfaseObrigatorias)}
                    {customSubList([], 'Optativas', handleClickNaoCursadasEnfaseOptativas, openNaoCursadasEnfaseOptativas)}
                </Collapse>

                <ListItemButton onClick={handleClickNaoCursadasPes}>
                    <ListItemText  primary={'Pes'} />
                    {openNaoCursadasPes ? <ExpandLess /> : <ExpandMore />}
                </ListItemButton>
                <Collapse in={openNaoCursadasPes} timeout="auto" unmountOnExit>
                    {listDisciplinas([])}
                </Collapse>
            </List>
        </Paper>
    );

    return (
        <Grid container spacing={2} justifyContent="center" alignItems="center" sx={css.root}>
            <Grid item>{customList(left.cursoObrigatorias, labels[0])}</Grid>
            <Grid item>
                <Grid container direction="column" alignItems="center">
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleAllRight}
                        disabled={left.cursoObrigatorias.length === 0}
                        aria-label="adicionar todas"
                    >
                        Adicionar todas (≫)
                    </Button>
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedRight}
                        disabled={leftCheckedCursoObrigatorias.length === 0}
                        aria-label="adicionar selecionadas"
                    >
                        Adicionar selecionadas (&gt;)
                    </Button>
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedLeft}
                        disabled={rightChecked.length === 0}
                        aria-label="remover selecionadas"
                    >
                        Remover selecionadas (&lt;)
                    </Button>
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleAllLeft}
                        disabled={right.length === 0}
                        aria-label="remover todas"
                    >
                        Remover todas (≪)
                    </Button>
                </Grid>
            </Grid>
            <Grid item>{customList(right, labels[1])}</Grid>
        </Grid>
    );
};

export default TransferList;