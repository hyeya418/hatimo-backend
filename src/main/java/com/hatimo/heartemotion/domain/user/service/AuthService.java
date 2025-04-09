package com.hatimo.heartemotion.domain.user.service;

import com.hatimo.heartemotion.domain.user.jwt.JwtProvider;
import com.hatimo.heartemotion.domain.user.model.User;
import com.hatimo.heartemotion.domain.user.service.KakaoLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

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

            return ResponseEntity.ok("✅ 로그인 성공! 안녕하세요, " + user.getNickname() + " 님 :)");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("❌ 카카오 로그인 실패: " + e.getMessage());
        }
    }
}
