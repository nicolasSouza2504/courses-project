import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom';
import UserRegisterScreen from "./screens/user/UserRegisterScreen";
import LoginScreen from "./screens/login/LoginScreen";
import CourseScreen  from "./screens/courses/CoursesScreen";
import './index.css';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<LoginScreen/>}/>
                <Route path="/user" element={<UserRegisterScreen/>}/>
                <Route path="/courses" element={<CourseScreen/>}/>
            </Routes>
        </Router>
    </React.StrictMode>
);

reportWebVitals();
