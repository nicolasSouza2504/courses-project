package br.com.courses.resource.course;


import br.com.courses.domain.course.CourseExibitionData;
import br.com.courses.domain.subscribe.RegisterSubscriptionDTO;
import br.com.courses.service.WebService;
import br.com.courses.service.course.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/courses")
public class CourseResource {

    private final WebService webService;

    @GetMapping
    public ResponseEntity<List> getAll() {
        return webService.get("/courses", List.class);
    }


    @PostMapping("/subscribe")
    public ResponseEntity subscribe(@RequestBody RegisterSubscriptionDTO registerSubscriptionDTO) {
        return webService.post("/courses/subscribe", registerSubscriptionDTO, ResponseEntity.class);
    }

}
