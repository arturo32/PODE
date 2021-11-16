import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from '../screen/home';
import Subject from '../screen/subject';

const Router = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/subject" element={<Subject />} />
                <Route path="*" element={<Home />} />
            </Routes>
        </BrowserRouter>
    );

};

export default Router;