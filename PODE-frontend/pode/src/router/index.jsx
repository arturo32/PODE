import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from '../screen/home';
import Login from '../screen/login';
import Register from '../screen/register';

const Router = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/cadastrar" element={<Register />} />
                <Route path="/entrar" element={<Login />} />
                <Route path="*" element={<Home />} />
            </Routes>
        </BrowserRouter>
    );

};

export default Router;