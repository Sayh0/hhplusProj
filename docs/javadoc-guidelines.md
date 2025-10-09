# JavaDoc 작성 가이드라인

## 개요
이 문서는 프로젝트에서 JavaDoc을 작성할 때 따라야 할 가이드라인입니다.

## 기본 원칙

### 1. 간결성과 명확성
- **간단하고 명확한 설명**을 작성합니다
- 핵심 정보만 포함합니다

### 2. 실무 중심
- 과도한 문서화보다는 **실용성**을 우선합니다
- 유지보수 부담을 최소화합니다

## 작성 규칙

### 클래스 레벨 JavaDoc

#### ✅ 권장 방식
```java
/**
 * API 공통 응답 클래스
 * 
 * @param <T> 응답 데이터의 타입
 */
public class ApiResponse<T> {
    // ...
}
```

### 메서드 레벨 JavaDoc

#### ✅ 권장 방식
```java
/**
 * 성공 응답 생성
 */
public static <T> ApiResponse<T> success(T data) {
    // ...
}

/**
 * 에러 응답 생성
 */
public static <T> ApiResponse<T> error(String code, String message) {
    // ...
}
```

#### 복잡한 메서드의 경우
```java
/**
 * 사용자 정보를 조회합니다.
 * 
 * @param userId 사용자 ID
 * @return 사용자 정보
 * @throws UserNotFoundException 사용자를 찾을 수 없는 경우
 */
public User getUser(Long userId) {
    // ...
}
```

### 필드 레벨 주석

#### ✅ 권장 방식 (인라인 주석)
```java
public class ApiResponse<T> {
    private boolean success;  // 성공 여부
    private T data;          // 응답 데이터
    private Error error;     // 에러 정보
    private String timestamp; // 응답 시간
}
```

### 테스트 메서드 JavaDoc

#### ✅ 권장 방식
```java
/**
 * 성공 응답 테스트
 */
@Test
void successResponse_shouldHaveCorrectStructure() {
    // ...
}

/**
 * 에러 응답 테스트 (code, message 직접 전달)
 */
@Test
void errorResponse_shouldHaveCorrectStructure() {
    // ...
}
```

## JavaDoc 작성 기준

### 1. 필수 작성 대상

#### ✅ **반드시 작성해야 하는 경우:**
- **공통 유틸리티 클래스** (ApiResponse, Error 등)
- **복잡한 비즈니스 로직** 메서드
- **외부 API** 인터페이스
- **라이브러리** 코드

#### ❌ **작성하지 않아도 되는 경우:**
- **단순한 VO/DTO** 클래스
- **명확한 변수명** (success, data 등)
- **Getter/Setter** 메서드
- **단순한 생성자**


### 2. 태그 사용 가이드

#### 필수 태그
- `@param`: 매개변수 설명 (복잡한 메서드만)
- `@return`: 반환값 설명 (복잡한 메서드만)
- `@throws`: 예외 설명 (예외를 던지는 경우만)

#### 선택적 태그
- `@deprecated`: 사용 중단된 메서드
- `@param <T>`: 제네릭 타입 설명

#### 사용하지 않는 태그
- `@author`: 개인 정보이므로 제외
- `@since`: 버전 관리로 충분
- `@version`: Git으로 관리

## 예시

### 좋은 예시
```java
/**
 * 주문을 생성하고 결제를 처리합니다.
 * 
 * @param orderRequest 주문 요청 정보
 * @return 생성된 주문 정보
 * @throws InsufficientStockException 재고 부족 시
 * @throws PaymentFailedException 결제 실패 시
 */
public Order createOrder(OrderRequest orderRequest) {
    // ...
}
```

### 나쁜 예시
```java
/**
 * 주문을 생성하고 결제를 처리합니다.
 * 
 * <p>이 메서드는 다음과 같은 단계를 거쳐 주문을 생성합니다:</p>
 * <ol>
 *   <li>재고 확인</li>
 *   <li>가격 계산 (할인 적용)</li>
 *   <li>결제 처리</li>
 *   <li>주문 생성</li>
 * </ol>
 * 
 * @param orderRequest 주문 요청 정보 (필수)
 * @return 생성된 주문 정보 (null이 아님)
 * @throws InsufficientStockException 재고가 부족한 경우
 * @throws PaymentFailedException 결제가 실패한 경우
 * @author 윤성훈
 * @since 1.0
 * @version 1.0
 */
public Order createOrder(OrderRequest orderRequest) {
    // ...
}
```
