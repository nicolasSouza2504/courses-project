package br.com.courses.service.course;

import br.com.courses.domain.course.CourseExibitionData;
import br.com.courses.domain.course.CourseRegisterDTO;
import br.com.courses.domain.course.CourseResponseData;
import br.com.courses.domain.subscribe.RegisterSubscriptionDTO;

import java.util.List;

public interface ICourseService {
    CourseResponseData create(CourseRegisterDTO courseRegisterDTO);
    List<CourseExibitionData> getAll();
    void subscribe(RegisterSubscriptionDTO registerSubscriptionDTO);
}
