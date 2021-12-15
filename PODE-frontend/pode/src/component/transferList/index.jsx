import React, { useState} from 'react';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';

import { css } from './styles';
import TransferListSubMenu from "../transferListSubMenu";

/*Retorna todos elementos do primeiro array que não estão no segundo*/
const not = (a, b) => {
    return a.filter((value) => b.indexOf(value) === -1);
};

/*Retorna a interseção dos dois arrays passados*/
const intersection = (a, b) => {
    return a.filter((value) => b.indexOf(value) !== -1);
};

const TransferList = (props) => {

    const { labels, left, right, handleChangeLeftCursoObrigatorias, handleChangeRightCursoObrigatorias,
        handleChangeLeftCursoOptativas, handleChangeRightCursoOptativas, handleChangeLeftEnfaseObrigatorias, handleChangeRightEnfaseObrigatorias,
        handleChangeLeftEnfaseOptativas, handleChangeRightEnfaseOptativas, handleChangeLeftPesObrigatorias, handleChangeRightPesObrigatorias} = props;

    const [checkedCursoObrigatorias, setCheckedCursoObrigatorias] = useState([]);
    const [checkedCursoOptativas, setCheckedCursoOptativas] = useState([]);

    const [checkedEnfaseObrigatorias, setCheckedEnfaseObrigatorias] = useState([]);
    const [checkedEnfaseOptativas, setCheckedEnfaseOptativas] = useState([]);

    const [checkedPes, setCheckedPes] = useState([]);


    const leftCheckedCursoObrigatorias = intersection(checkedCursoObrigatorias, left.cursoObrigatorias);
    const leftCheckedCursoOptativas = intersection(checkedCursoOptativas, left.cursoOptativas);

    const leftCheckedEnfaseObrigatorias = intersection(checkedEnfaseObrigatorias, left.enfaseObrigatorias);
    const leftCheckedEnfaseOptativas = intersection(checkedEnfaseOptativas, left.enfaseOptativas);

    const leftCheckedPes = intersection(checkedPes, left.pesObrigatorias);

    const rightChecked = intersection(checkedCursoObrigatorias, right.cursoObrigatorias);



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
        if(leftCheckedEnfaseObrigatorias.length !== 0){
            handleCheckedRightEspecifica(right.enfaseObrigatorias, left.enfaseObrigatorias, leftCheckedEnfaseObrigatorias,
                    checkedEnfaseObrigatorias, setCheckedEnfaseObrigatorias, handleChangeRightEnfaseObrigatorias, handleChangeLeftEnfaseObrigatorias);
        }
        if(leftCheckedEnfaseOptativas.length !== 0){
            handleCheckedRightEspecifica(right.enfaseOptativas, left.enfaseOptativas, leftCheckedEnfaseOptativas,
                    checkedEnfaseOptativas, setCheckedEnfaseOptativas, handleChangeRightEnfaseOptativas, handleChangeLeftEnfaseOptativas);
        }
        if(leftCheckedPes.length !== 0){
            handleCheckedRightEspecifica(right.pesObrigatorias, left.pesObrigatorias, leftCheckedPes,
                    checkedPes, setCheckedPes, handleChangeRightPesObrigatorias, handleChangeLeftPesObrigatorias);
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



    const customList = (items, label) => (
        <Paper sx={css.paper}>
            <Typography paragraph={true} gutterBottom={true} sx={css.title}>
                {label}
            </Typography>
            <List dense component="div" role="list">
                <TransferListSubMenu  disciplinas={
                    {
                        obrigatorias: {disciplinas: items.cursoObrigatorias,
                            checked: checkedCursoObrigatorias, setChecked: setCheckedCursoObrigatorias},
                        optativas: {disciplinas: items.cursoOptativas,
                            checked: checkedCursoOptativas, setChecked: setCheckedCursoOptativas}
                    }} nome={'Curso'}
                />
                <TransferListSubMenu  disciplinas={
                    {
                        obrigatorias: {disciplinas: items.enfaseObrigatorias,
                            checked: checkedEnfaseObrigatorias, setChecked: setCheckedEnfaseObrigatorias},
                        optativas: {disciplinas: items.enfaseOptativas,
                            checked: checkedEnfaseOptativas, setChecked: setCheckedEnfaseOptativas}
                    }} nome={'Ênfase'}
                />
                <TransferListSubMenu  disciplinas={
                    {
                        obrigatorias: {disciplinas: items.pesObrigatorias,
                            checked: checkedPes, setChecked: setCheckedPes}
                    }} nome={'PES'} ehPes
                />
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