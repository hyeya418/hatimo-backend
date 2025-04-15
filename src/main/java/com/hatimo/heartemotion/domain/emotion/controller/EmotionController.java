package com.hatimo.heartemotion.domain.emotion.controller;

import com.hatimo.heartemotion.domain.emotion.dto.EmotionRequestDto;
import com.hatimo.heartemotion.domain.emotion.model.Emotion;
import com.hatimo.heartemotion.domain.emotion.service.EmotionService;
import com.hatimo.heartemotion.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    @PostMapping
    public ApiResponse<Emotion> createEmotion(@Valid @RequestBody EmotionRequestDto request, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Emotion saved = emotionService.saveEmotion(userId, request);
        return ApiResponse.successSingle(saved);
    }
}
