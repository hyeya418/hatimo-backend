package com.hatimo.heartemotion.domain.hello.controller;

import com.hatimo.heartemotion.global.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return ApiResponse.successSingle("Hello, Hatimo!");
    }
}
