# 테스트 코드 작성 가이드라인

## 개요
이 문서는 프로젝트에서 테스트 코드를 작성할 때 따라야 할 가이드라인입니다.

## 1. 테스트 케이스 분류 체계

### A. 입력값 검증 테스트 (Input Validation Tests)
```
- 필수값 누락: null, 빈 문자열, 빈 컬렉션
- 데이터 타입: 잘못된 타입, 형식 오류
- 범위 검증: 최소값, 최대값, 음수, 0
- 길이 검증: 너무 짧음, 너무 김
```

### B. 비즈니스 규칙 테스트 (Business Rule Tests)
```
- 도메인 규칙: 금액은 0보다 커야 함
- 상태 규칙: 주문은 결제 완료 후에만 배송 가능
- 권한 규칙: 본인만 자신의 정보 수정 가능
- 제약 조건: 재고가 부족하면 주문 불가
```

### C. 예외 상황 테스트 (Exception Handling Tests)
```
- 데이터베이스 오류: 연결 실패, 트랜잭션 롤백
- 외부 API 오류: 결제 시스템 장애, 통신 오류
- 동시성 문제: 동시 접근, 락 충돌
- 리소스 부족: 메모리 부족, 타임아웃
```

### D. 성공 시나리오 테스트 (Success Scenario Tests)
```
- 정상 케이스: 모든 조건이 만족된 경우
- 경계값 케이스: 최소/최대 허용값
- 다양한 입력: 여러 유효한 입력 조합
```

## 2. 테스트 케이스 명명 규칙

### 패턴: `should[예상결과]When[상황]`

```java
// 예시
@Test
@DisplayName("고객 ID가 null이면 IllegalArgumentException 발생")
void shouldThrowExceptionWhenCustomerIdIsNull() {
    // Given-When-Then 패턴
}
```

### 명명 규칙 상세
- **should**: 테스트 의도를 명확히 표현
- **예상결과**: `ThrowException`, `ReturnSuccess`, `ReturnNull` 등
- **When**: 테스트 조건을 명확하게 표현
- **상황**: 구체적인 조건 설명

## 3. 테스트 우선순위 (실무 기준)

### 🔥 필수 (1순위)
1. **Null 체크** - NullPointerException 방지
2. **빈 값 체크** - 빈 문자열로 인한 비즈니스 로직 오류 방지
3. **음수/0 체크** - 비즈니스 규칙 위반 방지

### ⚡ 권장 (2순위)
4. **범위 초과 체크** - 시스템 안정성, 보안
5. **존재하지 않는 리소스** - 데이터 일관성, 외래키 제약

### 💡 선택 (3순위)
6. **동시성 테스트** - 멀티스레드 환경
7. **데이터베이스 오류** - 인프라 장애
8. **외부 API 오류** - 의존성 장애

## 4. 테스트 코드 작성 가이드

### 반복 사용되는 객체와 기본값은 @BeforeEach에서 준비
```java
  @BeforeEach
  void setUp() {
      // 여러 테스트에서 공통으로 사용하는 객체
      validRequest = new WalletChargeRequestVo(1L, 10000L, "CARD");
      customerId = 1L;
      chargeAmount = 10000L;
  }
```

### Given-When-Then 패턴
```java
@Test
@DisplayName("고객 ID가 null이면 IllegalArgumentException 발생")
void shouldThrowExceptionWhenCustomerIdIsNull() {
    // Given: 테스트 데이터 준비
    WalletChargeRequestVo request = new WalletChargeRequestVo(null, 10000L, "CARD");
    
    // When: 테스트 실행
    assertThatThrownBy(() -> walletService.chargePoint(request))
    
    // Then: 결과 검증
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("고객 ID는 필수입니다");
}
```

### 검증 방법
```java
// 예외 검증
assertThatThrownBy(() -> service.method())
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessage("에러 메시지");

// 반환값 검증
assertThat(result).isEqualTo(expected);

// 상태 검증
verify(mockService, never()).method();
```

## 5. 포인트 충전 기능 적용 예시

### 필수 테스트 (5개)
```java
// 1. Null 체크
shouldThrowExceptionWhenCustomerIdIsNull()
shouldThrowExceptionWhenPaymentMethodIsNull()

// 2. 빈 값 체크
shouldThrowExceptionWhenPaymentMethodIsEmpty()

// 3. 음수/0 체크
shouldThrowExceptionWhenChargeAmountIsNegative()
shouldThrowExceptionWhenChargeAmountIsZero()
```

### 권장 테스트 (추가)
```java
// 4. 범위 초과
shouldThrowExceptionWhenChargeAmountExceedsMax()

// 5. 존재하지 않는 리소스
shouldThrowExceptionWhenCustomerDoesNotExist()
```

## 6. 체크리스트

### 테스트 작성 전
- [ ] 요구사항에서 핵심 비즈니스 규칙 파악
- [ ] 입력값 검증 규칙 확인
- [ ] 예외 상황 정의

### 테스트 작성 중
- [ ] Given-When-Then 패턴 준수
- [ ] 명명 규칙 적용
- [ ] 1순위 테스트 우선 작성
- [ ] 명확한 에러 메시지 작성

### 테스트 작성 후
- [ ] 테스트 실행하여 실패 확인 (TDD)
- [ ] 최소한의 코드로 테스트 통과
- [ ] 리팩토링 후 테스트 재실행

---

**참고**: 이 가이드라인은 실무 효율성을 고려하여 작성되었으며, 프로젝트 특성에 따라 조정 가능합니다.
