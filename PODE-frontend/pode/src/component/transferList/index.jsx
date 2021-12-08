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

const not = (a, b) => {
    return a.filter((value) => b.indexOf(value) === -1);
};

const intersection = (a, b) => {
    return a.filter((value) => b.indexOf(value) !== -1);
};

const TransferList = (props) => {

    const { labels, left, right, handleChangeLeft, handleChangeRight } = props;

    const [checked, setChecked] = useState([]);

    const leftChecked = intersection(checked, left);
    const rightChecked = intersection(checked, right);

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
        handleChangeRight(right.concat(left));
        handleChangeLeft([]);
    };

    const handleCheckedRight = () => {
        handleChangeRight(right.concat(leftChecked));
        handleChangeLeft(not(left, leftChecked));
        setChecked(not(checked, leftChecked));
    };

    const handleCheckedLeft = () => {
        handleChangeLeft(left.concat(rightChecked));
        handleChangeRight(not(right, rightChecked));
        setChecked(not(checked, rightChecked));
    };

    const handleAllLeft = () => {
        handleChangeLeft(left.concat(right));
        handleChangeRight([]);
    };

    const customList = (items, label) => (
        <Paper sx={css.paper}>
            <Typography paragraph={true} gutterBottom={true} sx={css.title}>
                {label}
            </Typography>
            <List dense component="div" role="list">
                {items.map((value) => {
                    const labelId = `transfer-list-item-${value}-label`;
                    return (
                        <ListItem
                            key={value}
                            role="listitem"
                            button={true}
                            onClick={handleToggle(value)}
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
                <ListItem />
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
                        disabled={left.length === 0}
                        aria-label="adicionar todas"
                    >
                        Adicionar todas (≫)
                    </Button>
                    <Button
                        sx={css.button}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedRight}
                        disabled={leftChecked.length === 0}
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