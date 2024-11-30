import React, { useState, ChangeEvent, FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'universal-cookie';
import './Login.css';
import logo from '../../images/logo.svg';
import Response from '../../models/Response';

const Login: React.FC = () => {

    const [userName, setUserName] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [responseObj, setResponseObj] = useState<Response | null>(null);
    const navigate = useNavigate();

    const handleLogin = async (event: FormEvent<HTMLFormElement>) => {

        event.preventDefault();

        try {

            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userName, password }),
            });

            if (response.status === 200) {
                const session = await response.json();
                const cookies = new Cookies();
                cookies.set('authToken', session.authToken);
                setResponseObj({ message: 'Logged in successfully', error: false });
                navigate('/private-path');
            } else {
                const res = await response.json();
                setResponseObj({ message: `Error: ${res.message}`, error: true });
            }

        } catch (error) {
            setResponseObj({ message: 'Login failed. Please try again.', error: true });
        }

    };

    const handleUserNameChange = (e: ChangeEvent<HTMLInputElement>) => setUserName(e.target.value);

    const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => setPassword(e.target.value);

    return (
        <div className="App">
            <header className="App-header">
                <div>
                    <div className="Logo-div">
                        <h2>Login</h2>
                        <img src={logo} className="App-logo" alt="logo" />
                    </div>
                    <form onSubmit={handleLogin}>
                        <div className="Input-div">
                            <label htmlFor="userName">Username:</label>
                            <input
                                type="text"
                                id="userName"
                                value={userName}
                                onChange={handleUserNameChange}
                                required
                            />
                        </div>
                        <div className="Input-div">
                            <label htmlFor="password">Password:</label>
                            <input
                                type="password"
                                id="password"
                                value={password}
                                onChange={handlePasswordChange}
                                required
                            />
                        </div>
                        {responseObj && (
                            <div className={responseObj.error ? 'Message-response-error' : 'Message-response-success'}>
                                <p>{responseObj.message}</p>
                            </div>
                        )}
                        <button type="submit">Login</button>
                    </form>
                </div>
            </header>
        </div>
    );
};

export default Login;
