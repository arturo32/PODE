import React, { Fragment } from 'react';

import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';

import { css } from './styles';

const Alert = (props) => {

    const { open, type, message, close } = props;

    const handleClose = (_, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        close();
    };

    return (<Snackbar
        open={open}
        autoHideDuration={5000}
        onClose={handleClose}
        message={message}
        ContentProps={{ sx: css[type] }}
        action={
            <Fragment>
                <IconButton
                    aria-label="fechar"
                    color="inherit"
                    sx={css.icon}
                    onClick={handleClose}
                >
                    <CloseIcon />
                </IconButton>
            </Fragment>
        }
    />);

};

export default Alert;