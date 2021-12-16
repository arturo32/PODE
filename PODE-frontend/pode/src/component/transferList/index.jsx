import React, {useState} from 'react';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';

import { css } from './styles';
import TransferListSubMenu from "../transferListSubMenu";



const TransferList = (props) => {

    const { labels, left, right, handleChangeLeftCursoObrigatorias, handleChangeRightCursoObrigatorias,
        handleChangeLeftCursoOptativas, handleChangeRightCursoOptativas, handleChangeLeftEnfaseObrigatorias, handleChangeRightEnfaseObrigatorias,
        handleChangeLeftEnfaseOptativas, handleChangeRightEnfaseOptativas, handleChangeLeftPesObrigatorias, handleChangeRightPesObrigatorias,
        handleChangeLeftPesOptativas, handleChangeRightPesOptativas} = props;

    /*Retorna todos elementos do primeiro array que não estão no segundo*/
    const not = (a, b) => {
        return a.filter((value) => b.indexOf(value) === -1);
    };

    /*Retorna a interseção dos dois arrays passados*/
    const intersection = (a, b) => {
        return a.filter((value) => b.indexOf(value) !== -1);
    };

    const [checkedCursoObrigatorias, setCheckedCursoObrigatorias] = useState([]);
    const [checkedCursoOptativas, setCheckedCursoOptativas] = useState([]);

    const [checkedEnfaseObrigatorias, setCheckedEnfaseObrigatorias] = useState([]);
    const [checkedEnfaseOptativas, setCheckedEnfaseOptativas] = useState([]);

    const [checkedPesObrigatorias, setCheckedPesObrigatorias] = useState([]);
    const [checkedPesOptativas, setCheckedPesOptativas] = useState([]);


    const handleAllRight = () => {
        handleChangeRightCursoObrigatorias(right.concat(left.cursoObrigatorias));
        handleChangeLeftCursoObrigatorias([]);
    };

    let leftCheckedCursoObrigatorias = [];
    let leftCheckedCursoOptativas = [];
    let leftCheckedEnfaseObrigatorias = [];
    let leftCheckedEnfaseOptativas = [];
    let leftCheckedPesObrigatorias = [];
    let leftCheckedPesOptativas = [];

    let rightCheckedCursoObrigatorias = [];
    let rightCheckedCursoOptativas = [];
    let rightCheckedEnfaseObrigatorias = [];
    let rightCheckedEnfaseOptativas = [];
    let rightCheckedPesObrigatorias = [];
    let rightCheckedPesOptativas = [];



    const handleCheckedRight = () => {
        //Calcula interseção entre as selecionaodas, no geral, e as que estão no lado esquerdo
        leftCheckedCursoObrigatorias = intersection(checkedCursoObrigatorias, left.cursoObrigatorias);
        leftCheckedCursoOptativas = intersection(checkedCursoOptativas, left.cursoOptativas);
        leftCheckedEnfaseObrigatorias = intersection(checkedEnfaseObrigatorias, left.enfaseObrigatorias);
        leftCheckedEnfaseOptativas = intersection(checkedEnfaseOptativas, left.enfaseOptativas);
        leftCheckedPesObrigatorias = intersection(checkedPesObrigatorias, left.pesObrigatorias);
        leftCheckedPesOptativas = intersection(checkedPesOptativas, left.pesOptativas);

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
        if(leftCheckedPesObrigatorias.length !== 0){
            handleCheckedRightEspecifica(right.pesObrigatorias, left.pesObrigatorias, leftCheckedPesObrigatorias,
                    checkedPesObrigatorias, setCheckedPesObrigatorias, handleChangeRightPesObrigatorias, handleChangeLeftPesObrigatorias);
        }
        if(leftCheckedPesOptativas.length !== 0){
            handleCheckedRightEspecifica(right.pesOptativas, left.pesOptativas, leftCheckedPesOptativas,
                    checkedPesOptativas, setCheckedPesOptativas, handleChangeRightPesOptativas, handleChangeLeftPesOptativas);
        }

    };

    /*Passa as disciplinas selecionadas da coluna esquerda para a direita*/
    const handleCheckedRightEspecifica = (rightArray, leftArray, leftChecked, checked, setChecked, handleChangeRight, handleChangeLeft) => {
        /*Chama a função passada pelo elemento pai para lidar com a união das disciplinas checadas
        * com as que já estão do lado direito (acaba mudando right)*/
        handleChangeRight(rightArray.concat(leftChecked));

        /*Chama a função passada pelo elemento pai para lidar com as disciplinas NÃO selecionadas da
        * coluna da esquerda (acaba mudando left)*/
        handleChangeLeft(not(leftArray, leftChecked).filter(e => (leftChecked.find(e2 => e2.id === e.id) === undefined)));

        /*Atualiza checkedCursoObrigatorias, removendo as disciplinas que acabaram de serem
        * adicionadas à coluna da direita*/
        setChecked(not(checked, leftChecked));
    }

    /*Passa as disciplinas selecionadas da coluna direita para a esquerda*/
    const handleCheckedLeft = () => {

        /*Chama a função passada pelo elemento pai para lidar com a união das disciplinas checadas
        * da direita com as que já estão do lado esquerdo (acaba mudando left)*/
        handleChangeLeftCursoObrigatorias(left.cursoObrigatorias.concat(rightCheckedCursoObrigatorias));

        /*Chama a função passado pelo elemento pai para lidar com as disciplinas da coluna direita
        * que NÃO foram selecionadas (acaba mudando right)*/
        handleChangeRightCursoObrigatorias(not(right.cursoObrigatorias, rightCheckedCursoObrigatorias));

        /*Atualiza checkedCursoObrigatorias, removendo as disciplinas que acabaram de serem
        * adicionas a coluna da esquerda*/
        setCheckedCursoObrigatorias(not(checkedCursoObrigatorias, rightCheckedCursoObrigatorias));
    };

    const handleAllLeft = () => {
        handleChangeLeftCursoObrigatorias(left.cursoObrigatorias.concat(right));
        handleChangeRightCursoObrigatorias([]);
    };


    const isNenhumaDisciplinaEsquerdaAdicionada = () => {
        //Calcula interseção entre as selecionaodas, no geral, e as que estão no lado esquerdo
        leftCheckedCursoObrigatorias = intersection(checkedCursoObrigatorias, left.cursoObrigatorias);
        leftCheckedCursoOptativas = intersection(checkedCursoOptativas, left.cursoOptativas);
        leftCheckedEnfaseObrigatorias = intersection(checkedEnfaseObrigatorias, left.enfaseObrigatorias);
        leftCheckedEnfaseOptativas = intersection(checkedEnfaseOptativas, left.enfaseOptativas);
        leftCheckedPesObrigatorias = intersection(checkedPesObrigatorias, left.pesObrigatorias);
        leftCheckedPesOptativas = intersection(checkedPesOptativas, left.pesOptativas);
        return leftCheckedCursoObrigatorias.length === 0 &&
                leftCheckedCursoOptativas.length === 0 &&
                leftCheckedEnfaseObrigatorias.length === 0 &&
                leftCheckedEnfaseOptativas.length === 0 &&
                leftCheckedPesObrigatorias.length === 0 &&
                leftCheckedPesOptativas.length === 0;
    }

    const isNenhumaDisciplinaDireitaAdicionada = () => {
        return rightCheckedCursoObrigatorias.length === 0 &&
                rightCheckedCursoOptativas.length === 0 &&
                rightCheckedEnfaseObrigatorias.length === 0 &&
                rightCheckedEnfaseOptativas.length === 0 &&
                rightCheckedPesObrigatorias.length === 0 &&
                rightCheckedPesOptativas.length === 0;
    }

    const isLadoEsquerdoVazio = () => {
        for(let key in left){
            if(left[key].length !== 0){
                return false;
            }
        }
        return true;
    }

    const isLadoDireitoVazio = () => {
        for(let key in right){
            if(right[key].length !== 0){
                return false;
            }
        }
        return true;
    }



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
                            checked: checkedPesObrigatorias, setChecked: setCheckedPesObrigatorias},
                        optativas: {disciplinas: items.pesOptativas,
                            checked: checkedPesOptativas, setChecked: setCheckedPesOptativas}
                    }} nome={'PES'}
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
                        disabled={isLadoEsquerdoVazio()}
                        aria-label="adicionar todas"
                    >
                        Adicionar todas (≫)
                    </Button>
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedRight}
                        disabled={isNenhumaDisciplinaEsquerdaAdicionada()}
                        aria-label="adicionar selecionadas"
                    >
                        Adicionar selecionadas (&gt;)
                    </Button>
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedLeft}
                        disabled={isNenhumaDisciplinaDireitaAdicionada()}
                        aria-label="remover selecionadas"
                    >
                        Remover selecionadas (&lt;)
                    </Button>
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleAllLeft}
                        disabled={isLadoDireitoVazio()}
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