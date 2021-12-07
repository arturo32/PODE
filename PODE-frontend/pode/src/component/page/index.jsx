import React from 'react';

import Container from '@mui/material/Container';

import Header from '../header';
import Footer from '../footer';

const Page = (props) => {

    const { component } = props;

    return (
        <Container>
            <Header />
            {component}
            <Footer />
        </Container>
    );

};

export default Page;