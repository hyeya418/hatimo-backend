package com.hatimo.heartemotion.domain.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmotionCodeResponseDto {

    private String code;
    private String label;
    private String color;
}
