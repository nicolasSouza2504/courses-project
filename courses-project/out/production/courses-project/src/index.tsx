import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import UserRegisterScreen from "./screens/user/UserRegisterScreen";
import './index.css';
import LoginScreen from "./screens/login/LoginScreen";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <Router>
            <Routes>
                <Route path="/login" element={<LoginScreen/>}/>
                <Route path="/user" element={<UserRegisterScreen/>}/>
            </Routes>
        </Router>
    </React.StrictMode>
);

reportWebVitals();
