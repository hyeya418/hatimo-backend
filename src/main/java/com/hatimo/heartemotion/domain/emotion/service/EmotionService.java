package com.hatimo.heartemotion.domain.emotion.service;

import com.hatimo.heartemotion.domain.emotion.dto.EmotionRequestDto;
import com.hatimo.heartemotion.domain.emotion.model.Emotion;
import com.hatimo.heartemotion.domain.emotion.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;

    public Emotion saveEmotion(Long userId, EmotionRequestDto request) {
        Emotion emotion = Emotion.builder()
                .userId(userId)
                .emotionCode(request.getEmotionCode())
                .content(request.getContent())
                .build();

        return emotionRepository.save(emotion);
    }
}
