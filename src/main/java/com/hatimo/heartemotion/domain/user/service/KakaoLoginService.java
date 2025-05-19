package com.hatimo.heartemotion.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatimo.heartemotion.domain.user.model.User;
import com.hatimo.heartemotion.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public User loginWithKakao(String code) throws IOException, InterruptedException {
        // 1. 인가코드로 액세스 토큰 요청
        String accessToken = getAccessToken(code);

        // 2. 액세스 토큰으로 사용자 정보 요청
        JsonNode kakaoUser = getUserInfo(accessToken);

        System.out.println("[Kakao 사용자 정보 응답]");
        System.out.println(kakaoUser.toPrettyString());

        Long kakaoId = kakaoUser.path("id").asLong();
        String nickname = kakaoUser.path("properties").path("nickname").asText("사용자");
        String profileImg = kakaoUser.path("properties").path("profile_image").asText("");

        // 3. DB에 사용자 없으면 저장
        Optional<User> optionalUser = userRepository.findByKakaoId(kakaoId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User newUser = User.builder()
                    .kakaoId(kakaoId)
                    .nickname(nickname)
                    .profileImg(profileImg)
                    .createdAt(LocalDateTime.now())
                    .build();
            return userRepository.save(newUser);
        }
    }

    private String getAccessToken(String code) throws IOException, InterruptedException {
        String requestBody = "grant_type=authorization_code"
                + "&client_id=" + kakaoRestApiKey
                + "&redirect_uri=" + redirectUri
                + "&code=" + code;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kauth.kakao.com/oauth/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());

        // null 체크 추가
        if (jsonNode == null || !jsonNode.has("access_token")) {
            throw new RuntimeException("Failed to get access token from Kakao");
        }

        return jsonNode.get("access_token").asText();
    }

    private JsonNode getUserInfo(String accessToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kapi.kakao.com/v2/user/me"))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }
}
