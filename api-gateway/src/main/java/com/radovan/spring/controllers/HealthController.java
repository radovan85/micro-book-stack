package com.radovan.spring.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/health")
public class HealthController {

    @GetMapping
    public String healthCheck(HttpServletRequest request) {
        System.out.println("Health check called from: " + request.getRemoteAddr());
        return "OK";
    }
}
