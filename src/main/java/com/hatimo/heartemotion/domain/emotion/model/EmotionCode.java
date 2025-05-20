package com.hatimo.heartemotion.domain.emotion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emotion_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmotionCode {

    @Id
    @Column(length = 20)
    private String code;

    private String label;

    @Column(length = 20)
    private String color;

    @Column(name = "order_number")
    private Integer orderNumber;
}
