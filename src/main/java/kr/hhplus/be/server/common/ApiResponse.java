package kr.hhplus.be.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * API 공통 응답 클래스
 * 
 * @param <T> 응답 데이터의 타입
 */
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    
    private boolean success;  // 성공 여부
    private T data;          // 응답 데이터
    private Error error;     // 에러 정보
    private String timestamp; // 응답 시간

    /**
     * 성공 응답 생성
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, getCurrentTimestamp());
    }

    /**
     * 에러 응답 생성
     */
    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false, null, new Error(code, message), getCurrentTimestamp());
    }

    /**
     * 에러 응답 생성 (Error 객체 사용)
     */
    public static <T> ApiResponse<T> error(Error error) {
        return new ApiResponse<>(false, null, error, getCurrentTimestamp());
    }

    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

