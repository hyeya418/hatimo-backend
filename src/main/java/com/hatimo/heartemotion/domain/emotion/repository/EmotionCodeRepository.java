package com.hatimo.heartemotion.domain.emotion.repository;

import com.hatimo.heartemotion.domain.emotion.model.EmotionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmotionCodeRepository extends JpaRepository<EmotionCode, Long> {
    List<EmotionCode> findAllByOrderByOrderNumberAsc();
}