package com.hatimo.heartemotion.domain.emotion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class EmotionRecordResponseDto {

    private EmotionDto emotion;
    private String gptResponse;
}
