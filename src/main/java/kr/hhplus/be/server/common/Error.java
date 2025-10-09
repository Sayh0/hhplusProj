package kr.hhplus.be.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error {
    private String code;    // 에러 코드
    private String message; // 에러 메시지
}
