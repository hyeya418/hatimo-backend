package com.hatimo.heartemotion.domain.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class EmotionResponseDto {

    private Long id;             // 답변 ID
    private Long emotionId;      // 감정 ID
    private String response;     // GPT 응답 문장
    private LocalDateTime createdAt; // 생성 일시
}
