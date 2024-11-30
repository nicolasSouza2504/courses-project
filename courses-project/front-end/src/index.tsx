import React from 'react';
import ReactDOM from 'react-dom/client';
import './screens/login/Login.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './screens/login/LoginScreen';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
            </Routes>
        </Router>
    </React.StrictMode>
);

reportWebVitals();
