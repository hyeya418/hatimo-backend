package com.hatimo.heartemotion.domain.emotion.repository;

import com.hatimo.heartemotion.domain.emotion.model.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Optional<Emotion> findByIdAndUserId(Long id, Long userId);
}
