import Service from "../Service";
import {AxiosResponse} from "axios";
import UserRegister from "../../models/user/UserRegister";

const UserRegisterService =  {

    createUser: async (user: UserRegister): Promise<AxiosResponse> => {

        let response: AxiosResponse = await Service.post('/user', user);

        return response;

    }

}


export default UserRegisterService;
