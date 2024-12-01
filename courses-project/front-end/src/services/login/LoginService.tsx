import React from "react";

import Service from "../Service";
import {isSession, Session} from "../../models/Session";
import {AxiosResponse} from "axios";
import UserLogin from "../../models/user/UserLogin";

const UserLoginService =  {

    login: async (user: UserLogin): Promise<AxiosResponse> => {

        let response: AxiosResponse = await Service.post('/auth/login', user);

        if (response.data && isSession(response.data.data)) {

            let session: Session = response.data.data;

            localStorage.setItem("auth-token", session.token);

            return response;

        } else {
            return response;
        }

        return response;

    }

}


export default UserLoginService;
