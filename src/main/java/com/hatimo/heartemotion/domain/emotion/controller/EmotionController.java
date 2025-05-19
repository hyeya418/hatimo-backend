package com.hatimo.heartemotion.domain.emotion.controller;

import com.hatimo.heartemotion.domain.emotion.dto.EmotionCodeResponseDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionRequestDto;
import com.hatimo.heartemotion.domain.emotion.model.Emotion;
import com.hatimo.heartemotion.domain.emotion.service.EmotionService;
import com.hatimo.heartemotion.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    @PostMapping("/create")
    public ApiResponse<Emotion> createEmotion(@Valid @RequestBody EmotionRequestDto request, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Emotion saved = emotionService.saveEmotion(userId, request);
        return ApiResponse.successSingle(saved);
    }

    @PostMapping("/{emotionId}/response")
    public ApiResponse<String> getEmotionResponse(@PathVariable Long emotionId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        String aiResponse = emotionService.getEmotionResponse(userId, emotionId);
        return ApiResponse.successSingle(aiResponse);
    }

    @GetMapping("/codes")
    public ApiResponse<EmotionCodeResponseDto> getEmotionCodes() {
        List<EmotionCodeResponseDto> codes = emotionService.getEmotionCodes();
        return ApiResponse.successList(codes);
    }

}
