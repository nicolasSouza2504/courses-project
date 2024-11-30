import React from "react";

import Service from "../Service";
import {isSession, Session} from "../../models/Session";
import {AxiosResponse} from "axios";
import UserLogin from "../../models/user/UserLogin";

const UserLoginService =  {

    login: async (user: UserLogin): Promise<AxiosResponse> => {

        let response: AxiosResponse<Session> = await Service.post('/auth/login', user);

        if (isSession(response.data)) {

            let session: Session = response.data;

            localStorage.setItem("auth-token", session.token);

            return response;

        } else {
            return response;
        }

        return response;

    }

}


export default UserLoginService;
