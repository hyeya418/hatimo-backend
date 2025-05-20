package com.hatimo.heartemotion.domain.emotion.controller;

import com.hatimo.heartemotion.domain.emotion.dto.EmotionCodeDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionRecordResponseDto;
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

    @GetMapping("/codes")
    public ApiResponse<EmotionCodeDto> getEmotionCodes() {
        List<EmotionCodeDto> codes = emotionService.getEmotionCodes();
        return ApiResponse.successList(codes);
    }

    @PostMapping("/record")
    public ApiResponse<EmotionRecordResponseDto> recordEmotion(@Valid @RequestBody EmotionRequestDto request, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Emotion saved = emotionService.saveEmotion(userId, request);
        String gptResponse = emotionService.getEmotionResponse(userId, saved.getId());

        EmotionDto emotionDto = EmotionDto.builder()
                .id(saved.getId())
                .emotionCode(saved.getEmotionCode())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .build();

        EmotionRecordResponseDto result = EmotionRecordResponseDto.builder()
                .emotion(emotionDto)
                .gptResponse(gptResponse)
                .build();

        return ApiResponse.successSingle(result);
    }

}
