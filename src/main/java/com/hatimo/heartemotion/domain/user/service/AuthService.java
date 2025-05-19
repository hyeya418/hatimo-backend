package com.hatimo.heartemotion.domain.user.service;

import com.hatimo.heartemotion.domain.user.jwt.JwtProvider;
import com.hatimo.heartemotion.domain.user.model.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthService {

    private Logger logger;
    private final KakaoLoginService kakaoLoginService;
    private final JwtProvider jwtProvider;

    public ResponseEntity<String> loginWithKakao(String code, HttpServletResponse response) {
        try {
            User user = kakaoLoginService.loginWithKakao(code);
            String token = jwtProvider.createToken(user.getId());

            ResponseCookie cookie = ResponseCookie.from("access_token", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(3600)
                    .sameSite("Lax")
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());

            response.sendRedirect("http://localhost:3000/main");

            return ResponseEntity.ok("✅ 로그인 성공! 안녕하세요, " + user.getNickname() + " 님 :)");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "카카오 로그인 실패");
            } catch (IOException ioException) {
                ioException.printStackTrace();
                logger.info("❌ 카카오 로그인 실패 - 코드: " + code + ", 에러: " + e.getMessage());
            }
            return ResponseEntity.status(401).body("❌ 로그인 실패");
        }
    }

}
