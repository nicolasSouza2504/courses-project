package br.com.backendapi.resource.course;

import br.com.backendapi.domain.course.CourseExibitionData;
import br.com.backendapi.domain.course.CourseRegisterDTO;
import br.com.backendapi.domain.course.CourseResponseData;
import br.com.backendapi.domain.subscribe.RegisterSubscriptionDTO;
import br.com.backendapi.service.course.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/courses")
public class CourseResource {

    private final ICourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseData> register(@RequestBody CourseRegisterDTO courseRegisterDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.create(courseRegisterDTO));


    }

    @GetMapping
    public ResponseEntity<List<CourseExibitionData>> getAll() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.getAll());

    }


    @PostMapping("/subscribe")
    public ResponseEntity subscribe(@RequestBody RegisterSubscriptionDTO registerSubscriptionDTO) {

        courseService.subscribe(registerSubscriptionDTO);

        return ResponseEntity.ok("Inscrição realizada com sucesso");

    }

}
