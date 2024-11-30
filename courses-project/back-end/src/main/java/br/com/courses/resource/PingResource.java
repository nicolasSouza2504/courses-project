package br.com.courses.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ping")
public class PingResource {

    @GetMapping
    public String ping() {
        return "pong";
    }

}
