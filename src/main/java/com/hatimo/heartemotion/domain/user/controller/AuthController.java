package com.hatimo.heartemotion.domain.user.controller;

import com.hatimo.heartemotion.domain.user.jwt.JwtProvider;
import com.hatimo.heartemotion.domain.user.model.User;
import com.hatimo.heartemotion.domain.user.service.AuthService;
import com.hatimo.heartemotion.domain.user.service.KakaoLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code, HttpServletResponse response) {
        return authService.loginWithKakao(code, response);
    }

}
