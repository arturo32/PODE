import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import PaginaInicial from '../tela/inicio';
import PaginaDisciplinas from '../tela/disciplinas';

const Router = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/disciplinas" element={<PaginaDisciplinas />} />
                <Route path="*" element={<PaginaInicial />} />
            </Routes>
        </BrowserRouter>
    );

};

export default Router;