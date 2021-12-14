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

/*Retorna todos elementos do primeiro array que não estão no segundo*/
const not = (a, b) => {
    return a.filter((value) => b.indexOf(value) === -1);
};

/*Retorna a interseção dos dois arrays passados*/
const intersection = (a, b) => {
    return a.filter((value) => b.indexOf(value) !== -1);
};

const TransferList = (props) => {

    const { labels, left, right, handleChangeLeftCursoObrigatorias, handleChangeRightCursoObrigatorias, handleChangeLeftCursoOptativas, handleChangeRightCursoOptativas } = props;

    const [checkedCursoObrigatorias, setCheckedCursoObrigatorias] = useState([]);
    const [checkedCursoOptativas, setCheckedCursoOptativas] = useState([]);

    const [openNaoCursadasCurso, setOpenNaoCursadasCurso] = useState(true);
    const [openNaoCursadasCursoObrigatorias, setOpenNaoCursadasCursoObrigatorias] = useState(true);
    const [openNaoCursadasCursoOptativas, setOpenNaoCursadasCursoOptativas] = useState(true);

    const [openNaoCursadasEnfase, setOpenNaoCursadasEnfase] = useState(true);
    const [openNaoCursadasEnfaseObrigatorias, setOpenNaoCursadasEnfaseObrigatorias] = useState(true);
    const [openNaoCursadasEnfaseOptativas, setOpenNaoCursadasEnfaseOptativas] = useState(true);

    const [openNaoCursadasPes, setOpenNaoCursadasPes] = useState(true);


    const leftCheckedCursoObrigatorias = intersection(checkedCursoObrigatorias, left.cursoObrigatorias);
    const leftCheckedCursoOptativas = intersection(checkedCursoOptativas, left.cursoOptativas);
    const rightChecked = intersection(checkedCursoObrigatorias, right.cursoObrigatorias);


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
        if(value.tipo === 'CURSO_OBRIGATORIA') {
            handleToggleEspecifica(value, checkedCursoObrigatorias, setCheckedCursoObrigatorias);
        } else if(value.tipo === 'CURSO_OPTATIVA'){
            handleToggleEspecifica(value, checkedCursoOptativas, setCheckedCursoOptativas);
        }
    };

    const checkboxChecked = (value) => {
        if(value.tipo === 'CURSO_OBRIGATORIA'){
            return checkedCursoObrigatorias.indexOf(value) !== -1;
        } else if(value.tipo === 'CURSO_OPTATIVA'){
            return checkedCursoOptativas.indexOf(value) !== -1;
        }
    };

    /*Se o nome da disciplina passado não está presente no array checkedCursoObrigatorias, ele é adicionado.
    * Se estiver presente, é removido.*/
    const handleToggleEspecifica = (value, checkedArray, setCheckedArray) => {
        const currentIndex = checkedArray.indexOf(value);
        const newChecked = [...checkedArray];
        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }
        setCheckedArray(newChecked);
    }

    const handleAllRight = () => {
        handleChangeRightCursoObrigatorias(right.concat(left.cursoObrigatorias));
        handleChangeLeftCursoObrigatorias([]);
    };

    const handleCheckedRight = () => {
        if(leftCheckedCursoObrigatorias.length !== 0){
            handleCheckedRightEspecifica(right.cursoObrigatorias, left.cursoObrigatorias, leftCheckedCursoObrigatorias,
                    checkedCursoObrigatorias, setCheckedCursoObrigatorias, handleChangeRightCursoObrigatorias, handleChangeLeftCursoObrigatorias);
        }
        if(leftCheckedCursoOptativas.length !== 0){
            handleCheckedRightEspecifica(right.cursoOptativas, left.cursoOptativas, leftCheckedCursoOptativas,
                    checkedCursoOptativas, setCheckedCursoOptativas, handleChangeRightCursoOptativas, handleChangeLeftCursoOptativas);
        }
    };

    /*Passa as disciplinas selecionadas da coluna esquerda para a direita*/
    const handleCheckedRightEspecifica = (rightArray, leftArray, leftChecked, checked, setChecked, handleChangeRight, handleChangeLeft) => {
        /*Chama a função passada pelo elemento pai para lidar com a união das disciplinas checadas
        * com as que já estão do lado direito (acaba mudando right)*/
        handleChangeRight(rightArray.concat(leftChecked));

        /*Chama a função passada pelo elemento pai para lidar com as disciplinas NÃO selecionadas da
        * coluna da esquerda (acaba mudando left)*/
        handleChangeLeft(not(leftArray, leftChecked));

        /*Atualiza checkedCursoObrigatorias, removendo as disciplinas que acabaram de serem
        * adicionadas a coluna da direita*/
        setChecked(not(checked, leftChecked));
    }

    /*Passa as disciplinas selecionadas da coluna direita para a esquerda*/
    const handleCheckedLeft = () => {

        /*Chama a função passada pelo elemento pai para lidar com a união das disciplinas checadas
        * da direita com as que já estão do lado esquerdo (acaba mudando left)*/
        handleChangeLeftCursoObrigatorias(left.cursoObrigatorias.concat(rightChecked));

        /*Chama a função passado pelo elemento pai para lidar com as disciplinas da coluna direita
        * que NÃO foram selecionadas (acaba mudando right)*/
        handleChangeRightCursoObrigatorias(not(right.cursoObrigatorias, rightChecked));

        /*Atualiza checkedCursoObrigatorias, removendo as disciplinas que acabaram de serem
        * adicionas a coluna da esquerda*/
        setCheckedCursoObrigatorias(not(checkedCursoObrigatorias, rightChecked));
    };

    const handleAllLeft = () => {
        handleChangeLeftCursoObrigatorias(left.cursoObrigatorias.concat(right));
        handleChangeRightCursoObrigatorias([]);
    };



    const listDisciplinas = (disciplinas) => (
        <List component="div" disablePadding >
            {disciplinas.map((value) => {
                const labelId = `transfer-list-item-${value}-label`;
                return (
                    <ListItem
                            key={value.id}
                            role="listitem"
                            button={true}
                            onClick={handleToggle(value)}
                            sx={{ pl: 8 }}
                    >
                        <ListItemIcon>
                            <Checkbox
                                    checked={checkboxChecked(value)}
                                    tabIndex={-1}
                                    disableRipple
                                    inputProps={{
                                        'aria-labelledby': labelId,
                                    }}
                            />
                        </ListItemIcon>
                        <ListItemText id={labelId} primary={value.nome} />
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
            <Collapse in={openState} timeout={400} unmountOnExit>
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
                    {customSubList(items.cursoObrigatorias, 'Obrigatórias', handleClickNaoCursadasCursoObrigatorias, openNaoCursadasCursoObrigatorias)}
                    {customSubList(items.cursoOptativas, 'Optativas', handleClickNaoCursadasCursoOptativas, openNaoCursadasCursoOptativas)}
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
            <Grid item>{customList(left, labels[0])}</Grid>
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
                        disabled={leftCheckedCursoObrigatorias.length === 0 && leftCheckedCursoOptativas.length === 0}
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