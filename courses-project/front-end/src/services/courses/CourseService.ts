import Service from "../Service";
import {AxiosResponse} from "axios";
import Course from "../../models/courses/Course";
import RegisterSubscriptionDTO from "../../models/courses/RegisterSubscriptionDTO";


const CourseService =  {

    getAll: async (): Promise<AxiosResponse<Course[]>> => {

        let response: AxiosResponse = await Service.get('/courses');

        return response;

    },
    subscribe: async (subscriptionDTO: RegisterSubscriptionDTO):  Promise<AxiosResponse> => {

        const response: AxiosResponse =  await Service.post('/courses/subscribe', subscriptionDTO);

        return response;

    }

}


export default CourseService;
