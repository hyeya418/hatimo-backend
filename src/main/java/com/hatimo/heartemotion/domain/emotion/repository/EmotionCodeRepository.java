package com.hatimo.heartemotion.domain.emotion.repository;

import com.hatimo.heartemotion.domain.emotion.model.EmotionCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionCodeRepository extends JpaRepository<EmotionCode, String> {
}
