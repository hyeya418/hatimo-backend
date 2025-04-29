package com.hatimo.heartemotion.domain.emotion.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    public String askQuestion(String content) {
        // API 호출 로직 추가
        return "GPT 응답: [" + content + "]에 대한 답변입니다.";
    }
}
