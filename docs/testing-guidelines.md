# 테스트 코드 작성 가이드라인

## 개요

이 문서는 프로젝트에서 **TDD(Test-Driven Development)** 기반으로 테스트 코드를 작성할 때 따라야 할 가이드라인입니다.

### TDD 핵심 원칙

-   **항상 테스트 코드를 먼저 작성**하여 오류를 확인한 후 실제 코드를 작성
-   Red-Green-Refactor 사이클을 엄격히 준수
-   테스트가 실패하는 것을 먼저 확인한 후 최소한의 코드로 통과시키고 리팩토링

## 1. 테스트 케이스 분류

-   **입력값 검증**: null, 빈값, 음수/0, 범위 초과
-   **비즈니스 규칙**: 도메인 규칙, 상태 규칙, 권한 규칙
-   **예외 상황**: DB 오류, 외부 API 오류, 동시성 문제
-   **성공 시나리오**: 정상 케이스, 경계값 케이스

## 2. 테스트 명명 규칙

**패턴**: `should[예상결과]When[상황]`

```java
@Test
@DisplayName("고객 ID가 null이면 IllegalArgumentException 발생")
void shouldThrowExceptionWhenCustomerIdIsNull() {
    // Given-When-Then 패턴
}
```

## 3. TDD 테스트 우선순위

### 🔥 필수 (1순위)

1. **Null 체크** - NullPointerException 방지
2. **빈 값 체크** - 빈 문자열로 인한 비즈니스 로직 오류 방지
3. **음수/0 체크** - 비즈니스 규칙 위반 방지

### ⚡ 권장 (2순위)

4. **범위 초과 체크** - 시스템 안정성, 보안
5. **존재하지 않는 리소스** - 데이터 일관성, 외래키 제약

> **TDD 원칙**: 1순위 테스트를 먼저 작성하고 실패를 확인한 후, 최소한의 코드로 통과시킨 다음 2순위 테스트로 진행

## 4. TDD 테스트 작성 가이드

### Red 단계: 실패하는 테스트 작성

```java
@BeforeEach
void setUp() {
    validRequest = new WalletChargeRequestVo(1L, 10000L, "CARD");
}

@Test
@DisplayName("고객 ID가 null이면 IllegalArgumentException 발생")
void shouldThrowExceptionWhenCustomerIdIsNull() {
    // Given
    WalletChargeRequestVo request = new WalletChargeRequestVo(null, 10000L, "CARD");

    // When & Then
    assertThatThrownBy(() -> walletService.chargePoint(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("고객 ID는 필수입니다");
}
```

### Green 단계: 최소한의 코드로 통과

-   테스트를 통과하는 최소한의 코드만 작성
-   하드코딩된 값 사용 허용 (일시적)

### Refactor 단계: 코드 개선

-   테스트가 통과하는 상태에서만 리팩토링
-   하드코딩된 값들을 적절한 로직으로 교체
-   리팩토링 후 모든 테스트 재실행

## 5. 포인트 충전 기능 TDD 예시

### 필수 테스트 (1순위)

```java
shouldThrowExceptionWhenCustomerIdIsNull()
shouldThrowExceptionWhenPaymentMethodIsNull()
shouldThrowExceptionWhenPaymentMethodIsEmpty()
shouldThrowExceptionWhenChargeAmountIsNegative()
shouldThrowExceptionWhenChargeAmountIsZero()
```

### 권장 테스트 (2순위)

```java
shouldThrowExceptionWhenChargeAmountExceedsMax()
shouldThrowExceptionWhenCustomerDoesNotExist()
```

## 6. TDD 체크리스트

### Red 단계

-   [ ] Given-When-Then 패턴 준수
-   [ ] 명명 규칙 적용 (`should[예상결과]When[상황]`)
-   [ ] 1순위 테스트 우선 작성
-   [ ] **테스트 실행하여 실패 확인** (중요!)

### Green 단계

-   [ ] 테스트를 통과하는 최소한의 코드만 작성
-   [ ] 하드코딩된 값 사용 허용 (일시적)
-   [ ] 테스트 실행하여 통과 확인

### Refactor 단계

-   [ ] 테스트가 통과하는 상태에서만 리팩토링
-   [ ] 하드코딩된 값들을 적절한 로직으로 교체
-   [ ] 리팩토링 후 모든 테스트 재실행

---

**참고**: 이 가이드라인은 실무 효율성을 고려하여 작성되었으며, 프로젝트 특성에 따라 조정 가능합니다.
