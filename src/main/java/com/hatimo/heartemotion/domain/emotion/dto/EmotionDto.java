package com.hatimo.heartemotion.domain.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class EmotionDto {

    private Long id;
    private String emotionCode;
    private String content;
    private LocalDateTime createdAt;
}
