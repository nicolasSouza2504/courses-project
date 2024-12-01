package br.com.backendapi.repository;

import br.com.backendapi.domain.course.Course;
import br.com.backendapi.domain.course.CourseExibitionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Long> {

    boolean existsCourseByName(String name);

    @Query("SELECT new br.com.backendapi.domain.course.CourseExibitionData(" +
            "    c.id, " +
            "    c.name, " +
            "    COUNT(sub.id), " +
            "    CAST(c.avaiableSubscribes AS long)) " +
            "FROM Course c " +
            "LEFT JOIN Subscribe sub ON sub.idCourse = c.id " +
            "GROUP BY c.id, c.avaiableSubscribes, c.name " +
            "ORDER BY c.name")
    List<CourseExibitionData> findAllExibition();

    @Query( " SELECT (COUNT(sub.id) < c.avaiableSubscribes) " +
            " FROM Course c " +
            " LEFT JOIN Subscribe sub ON sub.idCourse = c.id " +
            " WHERE c.id = :id " +
            " GROUP BY c.id")
    boolean avaiableForSubscription(Long id);

}
