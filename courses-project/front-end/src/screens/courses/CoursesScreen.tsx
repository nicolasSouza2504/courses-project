import React, {useEffect, useState} from 'react';
import RegisterSubscriptionDTO from '../../models/courses/RegisterSubscriptionDTO';
import Course from '../../models/courses/Course';
import CourseService from "../../services/courses/CourseService";
import './CourseScreen.scss';

const CourseScreen: React.FC = () => {

    const [courses, setCourses] = useState<Course[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {

        const fetchCourses = async () => {

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

        };

        fetchCourses();
    }, []);

    const calculateVagasDisponiveis = (course: Course) => {
        return course.totalSubscribes - course.totalSubscriptions;
    };

    const handleEnrollment = (courseId: number) => {
        const registerData: RegisterSubscriptionDTO = {
            cpf: '12345678900', // You can replace this with dynamic user input
            courseId: courseId,
        };

        // Here you'd make the API call to enroll the user in the course
        // Example:
        // fetch('/api/enroll', {
        //   method: 'POST',
        //   body: JSON.stringify(registerData),
        //   headers: { 'Content-Type': 'application/json' },
        // });

        alert(`Enrolled in course with ID: ${courseId}`);
    };

    if (loading) return <p>Loading courses...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div className="course-list">
            <h1>Courses</h1>
            {courses.length > 0 ? (
                <table>
                    <thead>
                    <tr>
                        <th>Course Name</th>
                        <th>Total Subscribes</th>
                        <th>Total Subscriptions</th>
                        <th>Vagas Disponiveis</th>
                        <th>Vagas</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {courses.map((course) => {
                        const vagasDisponiveis = calculateVagasDisponiveis(course);
                        return (
                            <tr key={course.name}>
                                <td>{course.name}</td>
                                <td>{course.totalSubscribes}</td>
                                <td>{course.totalSubscriptions}</td>
                                <td>{vagasDisponiveis}</td>
                                <td>{course.totalSubscribes}</td>
                                <td>
                                    {vagasDisponiveis > 0 && (
                                        <button onClick={() => handleEnrollment(course.id)}>
                                            Enroll
                                        </button>
                                    )}
                                </td>
                            </tr>
                        );
                    })}
                    </tbody>
                </table>
            ) : (
                <p>No courses available.</p>
            )}
        </div>
    );
};

export default CourseScreen;
