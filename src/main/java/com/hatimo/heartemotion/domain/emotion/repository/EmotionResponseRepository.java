package com.hatimo.heartemotion.domain.emotion.repository;

import com.hatimo.heartemotion.domain.emotion.model.EmotionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmotionResponseRepository extends JpaRepository<EmotionResponse, Long> {

    Optional<EmotionResponse> findByEmotionId(Long emotionId);
}
