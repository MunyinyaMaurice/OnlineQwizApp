package com.backend.online_qwiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping("/api/protected")
    public String getProtectedResource() {
        return "This is a protected resource!";
    }
}
