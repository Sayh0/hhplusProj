package kr.hhplus.be.server.common.exception;

import kr.hhplus.be.server.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리기
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 재고 부족 예외 처리
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientStock(InsufficientStockException e) {
        log.warn("재고 부족: {}", e.getMessage());
        return ResponseEntity.status(409)
                .body(ApiResponse.error("INSUFFICIENT_STOCK", e.getMessage()));
    }

    /**
     * 잔액 부족 예외 처리
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientBalance(InsufficientBalanceException e) {
        log.warn("잔액 부족: {}", e.getMessage());
        return ResponseEntity.status(409)
                .body(ApiResponse.error("INSUFFICIENT_BALANCE", e.getMessage()));
    }

    /**
     * 유효성 검증 실패 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("유효성 검증 실패: {}", e.getMessage());
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("유효하지 않은 요청입니다.");

        return ResponseEntity.badRequest()
                .body(ApiResponse.error("VALIDATION_ERROR", message));
    }

    /**
     * 잘못된 인수 예외 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("잘못된 인수: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("INVALID_ARGUMENT", e.getMessage()));
    }

    /**
     * 모든 예외 처리 (fallback)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception e) {
        log.error("예상치 못한 오류 발생", e);
        return ResponseEntity.status(500)
                .body(ApiResponse.error("INTERNAL_ERROR", "서버 오류가 발생했습니다."));
    }
}
