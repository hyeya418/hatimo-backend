package com.hatimo.heartemotion.domain.emotion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionRequestDto {

    @NotBlank
    private String emotionCode; // 예: "JOY", "SAD"

    private String content; // 감정 텍스트
}
