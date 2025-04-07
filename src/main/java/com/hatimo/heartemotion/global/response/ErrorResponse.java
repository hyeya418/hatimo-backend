package com.hatimo.heartemotion.global.response;

import java.util.List;

public record ErrorResponse(
        String code,
        String message,
        List<Object> data // 항상 배열 형태로 유지
) {
    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, List.of());
    }
}