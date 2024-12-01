package br.com.courses.service.course;

import br.com.courses.domain.course.CourseExibitionData;
import br.com.courses.domain.course.CourseRegisterDTO;
import br.com.courses.domain.course.CourseResponseData;
import br.com.courses.domain.subscribe.RegisterSubscriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    @Override
    public CourseResponseData create(CourseRegisterDTO courseRegisterDTO) {
        return null;
    }

    @Override
    public List<CourseExibitionData> getAll() {
        return null;
    }

    @Override
    public void subscribe(RegisterSubscriptionDTO registerSubscriptionDTO) {
    }


}
