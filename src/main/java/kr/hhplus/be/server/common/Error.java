package kr.hhplus.be.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Error {
    private final String code;    // 에러 코드
    private final String message; // 에러 메시지
}
