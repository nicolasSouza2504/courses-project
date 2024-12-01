import React, {ChangeEvent, useState} from 'react';
import './Login.scss';
import logo from '../../images/logo.svg';
import Response from '../../models/Response';
import LoginService from "../../services/login/LoginService";
import UserLogin from "../../models/user/UserLogin";
import InputMask from "react-input-mask";
import NotificationComponent from "../../components/notification/NotificationComponent";
import {useNavigate} from "react-router-dom";
import {ReactNotifications} from "react-notifications-component";

const Login: React.FC = () => {

    const [email, setEmail] = useState<string>('');
    const [cpf, setCPF] = useState<string>("");
    const [password, setPassword] = useState<string>('');
    const [responseObj, setResponseObj] = useState<Response | null>(null);
    const navigate = useNavigate();

    const handleLogin = async () => {

        try {

            const response = await LoginService.login(buildLoginUser());

            if (response.status == 200) {

                NotificationComponent.triggerNotification("success", "Usuário autenticado com sucesso!", "Sucesso!");

                setTimeout(() => {
                    navigate('/courses');
                }, 3000);

            } else {
                NotificationComponent.triggerNotification("danger", "Erro ao autenticar!", "Erro!");
            }

        } catch (error: any) {

            if (error.response && error.response.data && error.response.data.data) {
                NotificationComponent.triggerNotification("danger", error.response.data.data, "Erro!");
            } else if (error.response && error.response.data && error.response.data.message){
                NotificationComponent.triggerNotification("danger", error.response.data.message, "Erro!");
            }
        }

    };

    const buildLoginUser = (): UserLogin =>  {
        return {
            email: email,
            cpf: cpf,
            password: password
        };
    }

    const handleEmailChange = (e: ChangeEvent<HTMLInputElement>) => setEmail(e.target.value);

    const handleCPFChange = (e: ChangeEvent<HTMLInputElement>) => setCPF(e.target.value);

    const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => setPassword(e.target.value);

    return (
        <div className="LoginContainer">
            <div className="Notifications">
                <ReactNotifications></ReactNotifications>
            </div>
            <div className="App">
                <header className="App-header">
                    <div>
                        <div className="Logo-div">
                            <img src={logo} className="App-logo" alt="logo"/>
                            <h2>Login</h2>
                        </div>
                        <div>
                            <div className="Input-div">
                                <label htmlFor="email">Email</label>
                                <input
                                    type="text"
                                    id="email"
                                    value={email}
                                    onChange={handleEmailChange}
                                    required
                                />
                            </div>
                            <div className="Input-div">
                                <label htmlFor="cpf">CPF</label>
                                <InputMask mask="999.999.999-99" maskChar=" " id="cpf" onChange={handleCPFChange}>
                                    {(inputProps: any) => <input {...inputProps} id="cpf" />}
                                </InputMask>
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
                            <span>Cadastre-se clicando <a href="user">aqui</a></span>
                        </div>
                    </div>
                </header>
            </div>
        </div>
    );
};

export default Login;
