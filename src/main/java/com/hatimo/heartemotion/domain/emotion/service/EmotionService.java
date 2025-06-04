package com.hatimo.heartemotion.domain.emotion.service;

import com.hatimo.heartemotion.domain.emotion.dto.EmotionCalendarEntryDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionCodeDto;
import com.hatimo.heartemotion.domain.emotion.dto.EmotionRequestDto;
import com.hatimo.heartemotion.domain.emotion.model.Emotion;
import com.hatimo.heartemotion.domain.emotion.model.EmotionCode;
import com.hatimo.heartemotion.domain.emotion.model.EmotionResponse;
import com.hatimo.heartemotion.domain.emotion.repository.EmotionCodeRepository;
import com.hatimo.heartemotion.domain.emotion.repository.EmotionRepository;
import com.hatimo.heartemotion.domain.emotion.repository.EmotionResponseRepository;
import com.hatimo.heartemotion.global.exception.CustomException;
import com.hatimo.heartemotion.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;
    private final EmotionResponseRepository emotionResponseRepository;
    private final EmotionCodeRepository emotionCodeRepository;
    private final OpenAiService openAiService;

    public Emotion saveEmotion(Long userId, @NotNull EmotionRequestDto request) {
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

    public List<EmotionCodeDto> getEmotionCodes() {
        return emotionCodeRepository.findAllByOrderByOrderNumberAsc().stream()
                .map(code -> EmotionCodeDto.builder()
                        .code(code.getCode())
                        .label(code.getLabel())
                        .color(code.getColor())
                        .orderNumber(code.getOrderNumber())
                        .build())
                .collect(Collectors.toList());
    }

    public Emotion getEmotionByIdAndUserId(Long emotionId, Long userId) {
        return emotionRepository.findByIdAndUserId(emotionId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.EMOTION_NOT_FOUND));
    }

    public String getGptResponseByEmotionId(Long emotionId) {
        EmotionResponse response = emotionResponseRepository.findByEmotionId(emotionId)
                .orElseThrow(() -> new CustomException(ErrorCode.EMOTION_RESPONSE_NOT_FOUND));
        return response.getResponse();
    }

    public Optional<Emotion> getEmotionByDate(Long userId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return emotionRepository.findOneByUserIdAndCreatedAtBetween(userId, start, end);
    }


    public List<EmotionCalendarEntryDto> getEmotionCalendarEntries(Long userId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Emotion> emotions = emotionRepository.findByUserIdAndCreatedAtBetween(
                userId,
                start.atStartOfDay(),
                end.atTime(LocalTime.MAX)
        );

        return emotions.stream()
                .map(e -> EmotionCalendarEntryDto.builder()
                        .date(e.getCreatedAt().toLocalDate().toString())
                        .emotionCode(e.getEmotionCode())
                        .build())
                .collect(Collectors.toList());
    }

    public String getColorByEmotionCode(String emotionCode) {
        return emotionCodeRepository.findById(emotionCode)
                .map(EmotionCode::getColor)
                .orElse("gray");
    }
}
