package com.hatimo.heartemotion.domain.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class EmotionResponseDto {
    private Long id;
    private String response;
    private LocalDateTime createdAt;
}
