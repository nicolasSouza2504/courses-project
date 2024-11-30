import axios, { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios';
import { API_BASE_URL } from '../constants/Constants'

const baseURL = API_BASE_URL
const timeout = 0;

const axiosInstance: AxiosInstance = axios.create({
    baseURL,
    timeout,
});

axiosInstance.interceptors.request.use(

    async (config: InternalAxiosRequestConfig): Promise<InternalAxiosRequestConfig> => {

        const authToken = await localStorage.getItem("auth-token");

        if (authToken) {
            config.headers['Cookie'] = 'authToken=' + authToken + ";"
        }

        return config;

    },
    (error: any) => {
        return Promise.reject(error);
    }

);

axiosInstance.interceptors.response.use(

    async (response: AxiosResponse): Promise<AxiosResponse> => {
        return response;
    },
    (error: any) => {

        if (error.response && error.response.status === 401) {

            console.log('Unauthorized, redirecting to login...');

            window.location.href = "/login";

        }

        return Promise.reject(error);

    }

);

export default axiosInstance;
