import React from 'react';

import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

import Home from '../screen/home';
import Login from '../screen/login';
import Register from '../screen/register';
import Platform from '../screen/platform';
import CoursePlan from '../screen/coursePlan';
import Recommendations from '../screen/recommendations';

import { get } from '../util/session';

const Router = () => {

    const protect = (component) => {
        let isAuthorized = false;
        if (get('user')) {
            isAuthorized = true;
        }
        return isAuthorized ? component : <Navigate to="/entrar" />;
    };

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/plataforma/plano-de-curso" element={protect(<Platform component={<CoursePlan />} />)} />
                <Route path="/plataforma/recomendacoes" element={protect(<Platform component={<Recommendations />} />)} />
                <Route path="/plataforma/*" element={protect(<Platform />)} />
                <Route path="/cadastrar" element={<Register />} />
                <Route path="/entrar" element={<Login />} />
                <Route path="*" element={<Home />} />
            </Routes>
        </BrowserRouter>
    );

};

export default Router;