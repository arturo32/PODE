import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
    palette: {
        primary: {
            main: '#F25C05',
        },
    },
});

export const css = {
    bar: {
        flexGrow: 1,
    },
    title: {
        flexGrow: 1,
        fontFamily: '"AlegreyaSans - Medium", sans-serif',
    },
    login: {
        marginLeft: '1em',
        marginRight: '1em',
    },
    register: {
        backgroundColor: '#F2B705',
    },
};