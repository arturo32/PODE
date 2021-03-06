import React, { useState } from 'react';

import { useTheme, ThemeProvider } from '@mui/material/styles';

import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ListItemText from '@mui/material/ListItemText';
import ListItemButton from "@mui/material/ListItemButton";

import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItemIcon from '@mui/material/ListItemIcon';
import SettingsIcon from '@mui/icons-material/Settings';
import AssistantIcon from '@mui/icons-material/Assistant';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';

import { generic } from '../../component/theme';
import Tooltip from '../../component/tooltip';

import { clear } from '../../util/session';

import { Main, AppBar, DrawerHeader, css } from './styles';

const PersistentDrawerLeft = (props) => {

    const { component } = props;

    const theme = useTheme();

    const [open, setOpen] = useState(true);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    const logout = () => {
        clear();
        window.location = '/';
    };

    return (
        <Box sx={css.root}>
            <CssBaseline />
            <ThemeProvider theme={generic}>
                <AppBar position="fixed" open={open}>
                    <Toolbar>
                        <IconButton
                            color="inherit"
                            aria-label="abrir menu"
                            onClick={handleDrawerOpen}
                            edge="start"
                            sx={{ mr: 2, ...(open && { display: 'none' }) }}
                        >
                            <MenuIcon />
                        </IconButton>
                        <Typography variant="h6" noWrap component="div">
                            PODE
                        </Typography>
                        <Tooltip title="SAIR" placement="left">
                            <IconButton
                                color="inherit"
                                aria-label="sair"
                                onClick={() => logout()}
                                edge="start"
                                sx={css.logoutContext}
                            >
                                <ExitToAppIcon sx={css.logout} />
                            </IconButton>
                        </Tooltip>
                    </Toolbar>
                </AppBar>
            </ThemeProvider>
            <Drawer
                sx={css.drawer}
                variant="persistent"
                anchor="left"
                open={open}
            >
                <DrawerHeader>
                    <IconButton onClick={handleDrawerClose}>
                        {theme.direction === 'ltr' ? <ChevronLeftIcon sx={css.icon} /> : <ChevronRightIcon />}
                    </IconButton>
                </DrawerHeader>
                <Divider sx={css.dividir} />
                <List>
                    {[{ name: 'Plano de curso', icon: <SettingsIcon sx={css.icon} />, link: '/plataforma/plano-de-curso' },
                    { name: 'Recomenda????es', icon: <AssistantIcon sx={css.icon} />, link: '/plataforma/recomendacoes' }].map((item, index) => (
                        <ListItemButton key={index} component={'a'} href={item.link}>
                            <ListItemIcon>
                                {item.icon}
                            </ListItemIcon>
                            <ListItemText primary={item.name} />
                        </ListItemButton>
                    ))}
                </List>
            </Drawer>
            <Main open={open}>
                <DrawerHeader />
                {component}
            </Main>
        </Box>
    );
};

export default PersistentDrawerLeft;