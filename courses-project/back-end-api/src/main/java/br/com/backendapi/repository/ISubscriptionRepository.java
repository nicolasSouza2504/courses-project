package br.com.backendapi.repository;

import br.com.backendapi.domain.course.Course;
import br.com.backendapi.domain.course.CourseExibitionData;
import br.com.backendapi.domain.subscribe.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISubscriptionRepository extends JpaRepository<Subscribe, Long> {

    boolean existsByIdUserAndIdCourse(Long idUser, Long idCourse);

}
