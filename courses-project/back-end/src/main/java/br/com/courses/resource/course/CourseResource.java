package br.com.courses.resource.course;


import br.com.courses.domain.subscribe.RegisterSubscriptionDTO;
import br.com.courses.service.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseResource {

    private final WebService webService;

    @GetMapping
    public ResponseEntity<List> getAll() {
        return webService.get("/courses", List.class);
    }


    @PostMapping("/subscribe")
    public ResponseEntity subscribe(@RequestBody RegisterSubscriptionDTO registerSubscriptionDTO) {
        return webService.post("/courses/subscribe", registerSubscriptionDTO, Object.class);
    }

}
