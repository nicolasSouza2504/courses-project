import React, {ChangeEvent, useState} from 'react';
import './Login.scss';
import logo from '../../images/logo.svg';
import Response from '../../models/Response';
import LoginService from "../../services/login/LoginService";
import UserLogin from "../../models/user/UserLogin";

const Login: React.FC = () => {

    const [userName, setUserName] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [responseObj, setResponseObj] = useState<Response | null>(null);

    const handleLogin = async () => {

        try {
            const response = await LoginService.login(buildLoginUser());
        } catch (error) {
            console.log(error)
        }

    };

    const buildLoginUser = (): UserLogin =>  {
        return {
            [userName.indexOf("@") !== -1 ? 'email' : 'cpf']: userName,
            password: password
        };
    }

    const handleUserNameChange = (e: ChangeEvent<HTMLInputElement>) => setUserName(e.target.value);

    const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => setPassword(e.target.value);

    return (
        <div className="LoginContainer">
            <div className="App">
                <header className="App-header">
                    <div>
                        <div className="Logo-div">
                            <h2>Login</h2>
                            <img src={logo} className="App-logo" alt="logo"/>
                        </div>
                        <div>
                            <div className="Input-div">
                                <label htmlFor="userName">Email/Cpf</label>
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
                                <div
                                    className={responseObj.error ? 'Message-response-error' : 'Message-response-success'}>
                                    <p>{responseObj.message}</p>
                                </div>
                            )}
                            <button onClick={handleLogin}>Login</button>
                        </div>
                    </div>
                </header>
            </div>
        </div>
    );
};

export default Login;
