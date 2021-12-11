export const css = {
    bar: {
        flexGrow: 1,
    },
    title: {
        flexGrow: 1,
        fontFamily: '"AlegreyaSans - Medium", sans-serif',
        textDecoration: 'none',
        color: 'white',
        ['@media (max-width:600px)']: {
            textAlign: 'center',
        },
    },
    toolbar: {
        ['@media (max-width:600px)']: {
            flexDirection: 'column',
            padding: '10px',
        },
    },
    buttons: {
        display: 'flex',
        flexDirection: 'row',
        ['@media (max-width:600px)']: {
            width: '100%',
            justifyContent: 'space-evenly',
            marginTop: '10px',
        },
    },
    login: {
        marginLeft: '1em',
        marginRight: '1em',
    },
    register: {
        backgroundColor: '#F2B705',
    },
};