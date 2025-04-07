package com.hatimo.heartemotion.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private Long kakaoId;

    private String nickname;
    private String email;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
