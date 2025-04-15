package com.hatimo.heartemotion.domain.emotion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmotionRequestDto {

    @NotBlank
    private String emotionCode; // 예: "JOY", "SAD"

    private String content; // 감정 텍스트

    // getter, setter
    public String getEmotionCode() {
        return emotionCode;
    }

    public void setEmotionCode(String emotionCode) {
        this.emotionCode = emotionCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
