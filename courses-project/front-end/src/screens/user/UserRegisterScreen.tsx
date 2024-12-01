import React, { useState } from "react";
import { useForm, Controller } from "react-hook-form";
import InputMask from "react-input-mask";
import "./UserRegisterScreen.scss";
import UserRegisterData from "../../models/user/UserRegister";
import UserRegisterService from "../../services/user/UserRegisterService";
import {useNavigate} from "react-router-dom";
import {ReactNotifications} from "react-notifications-component";
import NotificationComponent from "../../components/notification/NotificationComponent";


const UserRegisterScreen: React.FC = () => {

    const {control, handleSubmit, setError, formState: { errors } } = useForm<UserRegisterData>();
    const [backendErrors, setBackendErrors] = useState<{ [key: string]: string }>({});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();

    const onSubmit = async (data: UserRegisterData) => {

        setIsSubmitting(true);

        try {

            const response = await UserRegisterService.createUser(data);

            if (response && response.status === 201) {

                setIsSubmitting(false);

                NotificationComponent.triggerNotification("success", "Usuário cadastrado com sucesso!", "Sucesso!");

                setTimeout(() => {
                    navigate('/login');
                }, 3000);

            } else {

                NotificationComponent.triggerNotification("danger", "Erro ao cadastradar usuário!", "Error!");

                if (response?.data) {
                    setBackendErrors(response.data);
                }

                setIsSubmitting(false);

            }

        } catch (error: any) {

            if (error.response?.data) {

                setBackendErrors(error.response.data);

                if (typeof error.response.data === "string") {
                    NotificationComponent.triggerNotification("danger", "Erro ao cadastradar usuário: " + error.response?.data, "Error!");
                } else {
                    NotificationComponent.triggerNotification("danger", "Erro ao cadastradar usuário!", "Error!");
                }

            }

            setIsSubmitting(false);

        }

    };

    return (
        <div className="RegistrationContainer">
            <div className="Notifications">
                <ReactNotifications></ReactNotifications>
            </div>
            <form className="registration-form" onSubmit={handleSubmit(onSubmit)}>
                <h2>Cadastro de usuário</h2>
                <div className="input-group">
                    <label htmlFor="name">Nome</label>
                    <Controller
                        name="name"
                        control={control}
                        defaultValue=""
                        rules={{required: "Nome é obrigatório"}}
                        render={({field}) => <input {...field} type="text" id="name"/>}
                    />
                    {backendErrors.Name || errors.name ? <span className="error-message">{errors.name?.message || backendErrors.Name}</span> : <></>}
                </div>

                <div className="input-group">
                    <label htmlFor="email">Email</label>
                    <Controller
                        name="email"
                        control={control}
                        defaultValue=""
                        rules={{
                            pattern: {
                                value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                                message: "Formato de email inválido"
                            }
                        }}
                        render={({field}) => <input {...field} type="text" id="email"/>}
                    />
                    {backendErrors.Email || errors.email ?
                        <span className="error-message">{errors.email?.message || backendErrors.Email}</span>
                    : <></>}
                </div>

                <div className="input-group">
                    <label htmlFor="cpf">CPF</label>
                    <Controller
                        name="cpf"
                        control={control}
                        defaultValue=""
                        rules={{required: "CPF é obrigatório"}}
                        render={({field}) => (
                            <InputMask {...field} mask="999.999.999-99" maskChar=" " id="cpf"/>
                        )}
                    />
                    {backendErrors.CPF || errors.cpf ? <span className="error-message">{errors.cpf?.message || backendErrors.CPF}</span> : <></>}
                </div>

                <div className="input-group">
                    <label htmlFor="birthDate">Data de Nascimento</label>
                    <Controller
                        name="birthDate"
                        control={control}
                        defaultValue=""
                        rules={{
                            validate: (value) => new Date(value) > new Date() ? "Data de Nascimento não pode ser posterior a data atual" : undefined,
                        }}
                        render={({field}) => (
                            <input {...field} type="date" id="birthDate"/>
                        )}
                    />
                    {backendErrors.BirthDate || errors.birthDate ?
                        <span className="error-message">{errors.birthDate?.message || backendErrors.BirthDate}</span> : <></>}
                </div>
                <div className="input-group">
                    <label htmlFor="password">Senha</label>
                    <Controller
                        name="password"
                        control={control}
                        defaultValue=""
                        rules={{required: "Senha é obrigatória"}}
                        render={({field}) => <input {...field} type="password" id="password"/>}
                    />
                    {backendErrors.Password || errors.password ?
                        <span className="error-message">{errors.password?.message || backendErrors.Password}</span> : <></>}
                </div>
                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? "Enviando..." : "Cadastrar"}
                </button>
            </form>
        </div>
    );
};

export default UserRegisterScreen;
