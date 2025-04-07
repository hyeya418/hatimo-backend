package com.hatimo.heartemotion.domain.hello.controller;

import com.hatimo.heartemotion.domain.hello.dto.HelloRequestDto;
import com.hatimo.heartemotion.global.exception.CustomException;
import com.hatimo.heartemotion.global.exception.ErrorCode;
import com.hatimo.heartemotion.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return ApiResponse.successSingle("Hello, Hatimo!");
    }

    @GetMapping("/hello/error")
    public ApiResponse<String> errorTest() {
        throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }

    @PostMapping("/hello/validate")
    public ApiResponse<String> validateTest(@Valid @RequestBody HelloRequestDto request) {
        return ApiResponse.successSingle("성공!");
    }
}
