package kr.hhplus.be.server.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ApiResponse 클래스 테스트
 */
class ApiResponseTest {

    /**
     * 성공 응답 테스트
     */
    @Test
    void successResponse_shouldHaveCorrectStructure() {
        // Given
        String testData = "test data";

        // When
        ApiResponse<String> response = ApiResponse.success(testData);

        // Then
        assertTrue(response.isSuccess());
        assertEquals(testData, response.getData());
        assertNull(response.getError());
        assertNotNull(response.getTimestamp());
    }

    /**
     * 에러 응답 테스트 (code, message 직접 전달)
     */
    @Test
    void errorResponse_shouldHaveCorrectStructure() {
        // Given
        String errorCode = "TEST_ERROR";
        String errorMessage = "Test error message";

        // When
        ApiResponse<String> response = ApiResponse.error(errorCode, errorMessage);

        // Then
        assertFalse(response.isSuccess());
        assertNull(response.getData());
        assertNotNull(response.getError());
        assertEquals(errorCode, response.getError().getCode());
        assertEquals(errorMessage, response.getError().getMessage());
        assertNotNull(response.getTimestamp());
    }

    /**
     * 에러 응답 테스트 (Error 객체 전달)
     */
    @Test
    void errorResponseWithErrorObject_shouldHaveCorrectStructure() {
        // Given
        Error error = new Error("TEST_ERROR", "Test error message");

        // When
        ApiResponse<String> response = ApiResponse.error(error);

        // Then
        assertFalse(response.isSuccess());
        assertNull(response.getData());
        assertEquals(error, response.getError());
        assertNotNull(response.getTimestamp());
    }
}
