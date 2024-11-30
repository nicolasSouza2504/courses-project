import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './screens/login/LoginScreen';
import UserRegisterScreen from "./screens/user/UserRegisterScreen";
import './index.css';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/user" element={<UserRegisterScreen/>}/>
            </Routes>
        </Router>
    </React.StrictMode>
);

reportWebVitals();
