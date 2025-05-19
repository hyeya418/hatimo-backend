package com.hatimo.heartemotion.domain.emotion.service;

import com.hatimo.heartemotion.domain.emotion.dto.EmotionCodeResponseDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionRequestDto;
import com.hatimo.heartemotion.domain.emotion.model.Emotion;
import com.hatimo.heartemotion.domain.emotion.model.EmotionResponse;
import com.hatimo.heartemotion.domain.emotion.repository.EmotionCodeRepository;
import com.hatimo.heartemotion.domain.emotion.repository.EmotionRepository;
import com.hatimo.heartemotion.domain.emotion.repository.EmotionResponseRepository;
import com.hatimo.heartemotion.global.exception.CustomException;
import com.hatimo.heartemotion.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;
    private final EmotionResponseRepository emotionResponseRepository;
    private final EmotionCodeRepository emotionCodeRepository;
    private final OpenAiService openAiService;

    public Emotion saveEmotion(Long userId, EmotionRequestDto request) {
        Emotion emotion = Emotion.builder()
                .userId(userId)
                .emotionCode(request.getEmotionCode())
                .content(request.getContent())
                .build();

        return emotionRepository.save(emotion);
    }

    public String getEmotionResponse(Long userId, Long emotionId) {
        Emotion emotion = emotionRepository.findByIdAndUserId(emotionId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.EMOTION_NOT_FOUND));

        String gptResponse = openAiService.askQuestion(emotion.getContent());

        EmotionResponse emotionResponse = EmotionResponse.builder()
                .emotionId(emotionId)
                .response(gptResponse)
                .build();
        emotionResponseRepository.save(emotionResponse);

        return gptResponse;
    }

    public List<EmotionCodeResponseDto> getEmotionCodes() {
        return emotionCodeRepository.findAll().stream()
                .map(code -> EmotionCodeResponseDto.builder()
                        .code(code.getCode())
                        .label(code.getLabel())
                        .color(code.getColor())
                        .build())
                .collect(Collectors.toList());
    }

}
