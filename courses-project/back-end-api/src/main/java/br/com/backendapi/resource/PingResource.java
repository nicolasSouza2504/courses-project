package br.com.backendapi.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PingResource {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
