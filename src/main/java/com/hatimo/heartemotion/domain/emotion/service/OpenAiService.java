package com.hatimo.heartemotion.domain.emotion.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + openAiApiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String askQuestion(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", """
                                당신은 감정을 경청하고 공감해주는 전문 심리상담가입니다.
                                사용자가 전달하는 감정을 주의 깊게 읽고, 위로와 격려의 말을 따뜻하게 건네주세요.
                                """),
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );

        try {
            Map<String, Object> response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                String gptResponse = message.get("content").toString().trim();
                log.info("[GPT 응답] {}", gptResponse);
                return gptResponse;
            } else {
                return "GPT 응답을 찾을 수 없습니다.";
            }

        } catch (Exception e) {
            log.error("GPT 호출 실패", e);
            return "GPT 응답 중 오류가 발생했습니다.";
        }
    }
}
