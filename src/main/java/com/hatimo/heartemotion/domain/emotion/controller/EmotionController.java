package com.hatimo.heartemotion.domain.emotion.controller;

import ch.qos.logback.classic.Logger;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionCodeDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionRecordResponseDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionRequestDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionCalendarEntryDto;
import com.hatimo.heartemotion.domain.emotion.model.Emotion;
import com.hatimo.heartemotion.domain.emotion.service.EmotionService;
import com.hatimo.heartemotion.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

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

    @GetMapping("/codes")
    public ApiResponse<EmotionCodeDto> getEmotionCodes() {
        List<EmotionCodeDto> codes = emotionService.getEmotionCodes();
        return ApiResponse.successList(codes);
    }

    @GetMapping("/{emotionId}/response")
    public ApiResponse<EmotionRecordResponseDto> getEmotionWithGptResponse(@PathVariable Long emotionId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();

        Emotion emotion = emotionService.getEmotionByIdAndUserId(emotionId, userId);
        String gptResponse = emotionService.getGptResponseByEmotionId(emotionId);
        String color = emotionService.getColorByEmotionCode(emotion.getEmotionCode());

        EmotionDto emotionDto = EmotionDto.builder()
                .id(emotion.getId())
                .emotionCode(emotion.getEmotionCode())
                .content(emotion.getContent())
                .createdAt(emotion.getCreatedAt())
                .color(color)
                .build();

        EmotionRecordResponseDto result = EmotionRecordResponseDto.builder()
                .emotion(emotionDto)
                .gptResponse(gptResponse)
                .build();

        return ApiResponse.successSingle(result);
    }

    @GetMapping("/daily")
    public ApiResponse<EmotionDto> getDailyEmotion(@RequestParam("date") LocalDate date, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Optional<Emotion> optional = emotionService.getEmotionByDate(userId, date);

        if (optional.isEmpty()) {
            System.out.println("💡 Emotion not found for userId: " + userId + " date: " + date);
            return ApiResponse.success(null); // 또는 successSingle 내부 null-safe 처리 후 그대로
        }

        Emotion emotion = optional.get();
        EmotionDto dto = EmotionDto.builder()
                .id(emotion.getId())
                .emotionCode(emotion.getEmotionCode())
                .content(emotion.getContent())
                .createdAt(emotion.getCreatedAt())
                .build();

        return ApiResponse.successSingle(dto);
    }

    @GetMapping("/calendar")
    public ApiResponse<EmotionCalendarEntryDto> getEmotionCalendar(
            @RequestParam int year,
            @RequestParam int month,
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();
        List<EmotionCalendarEntryDto> result = emotionService.getEmotionCalendarEntries(userId, year, month);
        System.out.println(">>>>>>>>>>>>>>> result : " + result);

        return ApiResponse.successList(result);
    }
}
