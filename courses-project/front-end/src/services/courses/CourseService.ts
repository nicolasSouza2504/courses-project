import Service from "../Service";
import {AxiosResponse} from "axios";
import Course from "../../models/courses/Course";

const CourseService =  {

    getAll: async (): Promise<AxiosResponse<Course[]>> => {

        let response: AxiosResponse = await Service.get('/courses/');

        return response;

    }

}


export default CourseService;
