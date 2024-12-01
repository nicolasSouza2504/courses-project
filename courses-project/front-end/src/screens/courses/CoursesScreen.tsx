import React, { useEffect, useState } from 'react';
import RegisterSubscriptionDTO from '../../models/courses/RegisterSubscriptionDTO';
import Course from '../../models/courses/Course';
import CourseService from "../../services/courses/CourseService";
import './CourseScreen.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserPlus, faBan, faCheckCircle, faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import {AxiosResponse} from "axios";
import NotificationComponent from "../../components/notification/NotificationComponent";
import {ReactNotifications} from "react-notifications-component";
import {useLocation, useNavigate} from "react-router-dom";

const CourseScreen: React.FC = () => {

    const [courses, setCourses] = useState<Course[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const location = useLocation();
    const navigation = useNavigate();

    useEffect(() => {

        const fetchCourses = async () => {

            if (location.pathname == "/courses") {

                try {

                    const response = await CourseService.getAll(); // Replace with your actual API URL

                    if (response.status !== 200) {
                        throw new Error('Failed to fetch courses');
                    }

                    const data: Course[] = await response.data;

                    setCourses(data);

                } catch (err) {
                    setError((err as Error).message);
                } finally {
                    setLoading(false);
                }

                setTimeout(() => {
                    fetchCourses();
                },5000)
            }

        };

        fetchCourses();

    }, []);

    const calculateVagasDisponiveis = (course: Course) => {
        return course.totalSubscribes - course.totalSubscriptions;
    };

    const throwErrorNotification = (data: any) => {

        if (data) {
            Object.keys(data).forEach((key) => {
                NotificationComponent.triggerNotification('danger', data[key], "Erro");
            });
        }

    }

    const handleEnrollment = async (courseId: number) => {

        const registerData: RegisterSubscriptionDTO = {
            idCourse: courseId,
            cpf: localStorage.getItem("cpf")
        };

        try {

            const response: AxiosResponse = await CourseService.subscribe(registerData);

            if (response.status == 200) {
                NotificationComponent.triggerNotification('success', 'Usuário inscrito com sucesso', "Sucesso");
            } else {
                throwErrorNotification(response.data);
            }

        } catch (error:any) {
            throwErrorNotification(error.response.data);
        }

    };

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("cpf");
        navigation('/login');

    }

    if (loading) return <p className="loading-message">Loading courses...</p>;
    if (error) return <p className="error-message">Error: {error}</p>;

    return (
        <div className="course-list">
            <h1>Curso</h1>
            <ReactNotifications className="Notification"></ReactNotifications>
            <button className="logout-button" onClick={handleLogout}>
                <FontAwesomeIcon icon={faSignOutAlt}/> Logout
            </button>
            {courses.length > 0 ? (
                <div className="course-cards">
                    {courses.map((course) => {
                        const vagasDisponiveis = calculateVagasDisponiveis(course);
                        return (
                            <div key={course.id} className="course-card">
                                <div className="course-card-header">
                                    <h3>{course.name}</h3>
                                    <div className="course-icons">
                                        <div className="icon-box">
                                            <FontAwesomeIcon icon={faUserPlus}/>
                                            <p>{course.totalSubscriptions}</p>
                                        </div>
                                        <div className="icon-box">
                                            <FontAwesomeIcon icon={faCheckCircle}/>
                                            <p>{course.totalSubscribes}</p>
                                        </div>
                                    </div>
                                </div>
                                <div className="course-card-body">
                                    <p><strong>Vagas Disponíveis:</strong> {vagasDisponiveis}</p>
                                    <p><strong>Total Vagas:</strong> {course.totalSubscribes}</p>
                                </div>
                                <div className="course-card-footer">
                                    {vagasDisponiveis > 0 ? (
                                        <button className="enroll-button" onClick={() => handleEnrollment(course.id)}>
                                            <FontAwesomeIcon icon={faUserPlus}/> Inscrever-se
                                        </button>
                                    ) : (
                                        <button className="enroll-button disabled" disabled>
                                            <FontAwesomeIcon icon={faBan}/> Não há vagas
                                        </button>
                                    )}
                                </div>
                            </div>
                        );
                    })}
                </div>
            ) : (
                <p>No courses available.</p>
            )}
        </div>
    );
};

export default CourseScreen;
