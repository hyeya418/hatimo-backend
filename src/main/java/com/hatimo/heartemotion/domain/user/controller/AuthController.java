package com.hatimo.heartemotion.domain.user.controller;

import com.hatimo.heartemotion.domain.user.jwt.JwtProvider;
import com.hatimo.heartemotion.domain.user.model.User;
import com.hatimo.heartemotion.domain.user.service.KakaoLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoLoginService kakaoLoginService;
    private final JwtProvider jwtProvider;

    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
        try {
            // 1. 카카오 로그인 처리 → User 반환
            User user = kakaoLoginService.loginWithKakao(code);

            // 2. JWT 생성
            String token = jwtProvider.createToken(user.getId());

            // 3. 쿠키로 토큰 전달 (HttpOnly)
            response.setHeader("Set-Cookie",
                    "token=" + URLEncoder.encode(token, StandardCharsets.UTF_8)
                            + "; HttpOnly; Path=/; Max-Age=3600; SameSite=Lax");

            // 4. 프론트 리디렉션
            // response.sendRedirect("http://localhost:3000/"); // 프론트 홈 등으로 이동

            // 4. 테스트용 응답 메시지
            return "✅ 로그인 성공! 안녕하세요, " + user.getNickname() + " 님 :)";

        } catch (Exception e) {
            e.printStackTrace();
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "카카오 로그인 실패");
            return "❌ 카카오 로그인 실패: " + e.getMessage();
        }
    }
}
