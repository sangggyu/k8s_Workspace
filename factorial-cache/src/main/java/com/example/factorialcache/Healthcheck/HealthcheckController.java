package com.example.factorialcache.Healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("/probe/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}
