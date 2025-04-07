package com.hatimo.heartemotion.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // ✅ 공통
    INTERNAL_ERROR("INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    INVALID_INPUT("INVALID_INPUT", HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),
    ACCESS_DENIED("ACCESS_DENIED", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

    // ✅ 사용자
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD("INVALID_PASSWORD", HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // ✅ 감정 기록
    EMOTION_NOT_FOUND("EMOTION_NOT_FOUND", HttpStatus.NOT_FOUND, "감정 기록을 찾을 수 없습니다."),
    EMOTION_SAVE_FAILED("EMOTION_SAVE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR, "감정 기록 저장에 실패했습니다."),

    // ✅ 위로(GPT)
    COMFORT_FAILED("COMFORT_FAILED", HttpStatus.INTERNAL_SERVER_ERROR, "GPT 위로 처리 중 오류가 발생했습니다."),

    // ✅ 인증/토큰
    TOKEN_EXPIRED("TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_INVALID("TOKEN_INVALID", HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    TOKEN_MISSING("TOKEN_MISSING", HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}