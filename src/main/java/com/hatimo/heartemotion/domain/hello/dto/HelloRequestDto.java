package com.hatimo.heartemotion.domain.hello.dto;

import jakarta.validation.constraints.NotBlank;

public record HelloRequestDto(
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {}