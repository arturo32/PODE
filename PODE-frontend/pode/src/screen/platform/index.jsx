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
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import SettingsIcon from '@mui/icons-material/Settings';
import AssistantIcon from '@mui/icons-material/Assistant';

import { generic } from '../../component/theme';

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

    return (
        <Box sx={css.root}>
            <CssBaseline />
            <ThemeProvider theme={generic}>
                <AppBar position="fixed" open={open}>
                    <Toolbar>
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            onClick={handleDrawerOpen}
                            edge="start"
                            sx={{ mr: 2, ...(open && { display: 'none' }) }}
                        >
                            <MenuIcon />
                        </IconButton>
                        <Typography variant="h6" noWrap component="div">
                            PODE
                        </Typography>
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
                    {[{ name: 'Plano de curso', icon: <SettingsIcon sx={css.icon} /> }, { name: 'Recomendações', icon: <AssistantIcon sx={css.icon} /> }].map((item, index) => (
                        <ListItem button key={index}>
                            <ListItemIcon>
                                {item.icon}
                            </ListItemIcon>
                            <ListItemText primary={item.name} />
                        </ListItem>
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