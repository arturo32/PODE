import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from '../screen/home';
import Login from '../screen/login';
import Register from '../screen/register';
import Platform from '../screen/platform';
import CoursePlan from '../screen/coursePlan';
import Recommendations from '../screen/recommendations';

const Router = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/plataforma/plano-de-curso" element={<Platform component={<CoursePlan />} />} />
                <Route path="/plataforma/recomendacoes" element={<Platform component={<Recommendations />} />} />
                <Route path="/plataforma/*" element={<Platform />} />
                <Route path="/cadastrar" element={<Register />} />
                <Route path="/entrar" element={<Login />} />
                <Route path="*" element={<Home />} />
            </Routes>
        </BrowserRouter>
    );

};

export default Router;