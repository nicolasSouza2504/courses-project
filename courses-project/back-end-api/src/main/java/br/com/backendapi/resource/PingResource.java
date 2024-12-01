package br.com.courses.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}")
public class PingResource {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
