package br.com.backendapi.service.course;

import br.com.backendapi.domain.course.CourseExibitionData;
import br.com.backendapi.domain.course.CourseRegisterDTO;
import br.com.backendapi.domain.course.CourseResponseData;
import br.com.backendapi.domain.subscribe.RegisterSubscriptionDTO;

import java.util.List;

public interface ICourseService {
    CourseResponseData create(CourseRegisterDTO courseRegisterDTO);
    List<CourseExibitionData> getAll();
    void subscribe(RegisterSubscriptionDTO registerSubscriptionDTO);
}
