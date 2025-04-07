package com.hatimo.heartemotion.global.response;

import java.util.List;

public record ApiResponse<T>(
        String code,
        String message,
        List<T> data
) {
    public static <T> ApiResponse<T> success(List<T> data) {
        return new ApiResponse<>("SUCCESS", "요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> ApiResponse<T> successSingle(T singleData) {
        return success(List.of(singleData));
    }

    public static <T> ApiResponse<T> empty() {
        return new ApiResponse<>("SUCCESS", "요청이 성공적으로 처리되었습니다.", List.of());
    }
}