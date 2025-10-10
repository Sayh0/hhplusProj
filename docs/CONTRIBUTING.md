# 기여 가이드라인 (Contributing Guidelines)

## 브랜치 명명법 (Branch Naming Convention)

### 기본 구조

#### PR 피드백 반영용

```
{작업유형}/pr-{PR번호}-{작업내용}
```

#### 일반 기능 구현용

```
{작업유형}/{작업내용}
```

### 작업 유형별 분류

#### 1. 피드백 반영 (Feedback)

PR 리뷰어의 피드백을 반영하는 작업

```
feedback/pr-3-erd-table-structure
feedback/pr-3-javadoc-convention
feedback/pr-3-wallet-history-pk
```

#### 2. 기능 구현 (Feature)

새로운 기능을 구현하는 작업

```
feature/product-controller-tests
feature/order-api-implementation
feature/payment-integration
```

#### 3. 버그 수정 (Fix)

버그 수정이나 문제 해결

```
fix/product-service-null-check
fix/database-connection-issue
fix/test-failure-debug
```

#### 4. 리팩토링 (Refactor)

코드 개선이나 구조 변경

```
refactor/code-cleanup
refactor/architecture-improvement
```

#### 5. 문서화 (Docs)

문서 작성 및 수정

```
docs/api-spec-update
docs/readme-improvement
```

### 브랜치 명명 규칙

1. **소문자 사용**: 모든 브랜치명은 소문자로 작성
2. **하이픈 구분**: 단어 간 구분은 하이픈(-) 사용
3. **PR 피드백 반영시에만 PR 번호 포함**: `pr-{번호}` 형식 사용
4. **명확한 설명**: 작업 내용을 간결하고 명확하게 표현

### 예시

**✅ 올바른 예시:**

-   `feature/product-controller-tests` (일반 기능 구현)
-   `feedback/pr-3-erd-table-structure` (PR 피드백 반영)
-   `fix/wallet-history-pk-issue` (일반 버그 수정)

**❌ 잘못된 예시:**

-   `Feature/ProductControllerTests` (대문자 사용)
-   `feature_product_controller_tests` (언더스코어 사용)
-   `feature/pr-3-product-tests` (일반 기능인데 PR 번호 포함)

## 커밋 메시지 컨벤션

### 기본 구조

```
{타입}: {제목}

{본문 (선택사항)}

{푸터 (선택사항)}
```

### 커밋 타입

-   **feat**: 새로운 기능 추가
-   **fix**: 버그 수정
-   **docs**: 문서 수정
-   **style**: 코드 포맷팅, 세미콜론 누락 등
-   **refactor**: 코드 리팩토링
-   **test**: 테스트 코드 추가/수정
-   **chore**: 빌드 업무 수정, 패키지 매니저 설정 등

### 예시

```
feat: add ProductController with CRUD operations

- Add GET /products endpoint
- Add POST /products endpoint
- Add unit tests for all endpoints

Closes #123
```

## PR 작성 가이드

### PR 제목

```
[STEP-{번호}] {작업 내용}
```

### PR 설명 템플릿

<!--
  제목은 [(과제 STEP)] (작업한 내용) 로 작성해 주세요
  예시: [STEP-5] 이커머스 시스템 설계
-->

## 참고 자료

<!--
  (Optional: 참고 자료가 없는 작업 - 단순 버그 픽스 등 의 경우엔 해당 란을 제거해주세요 !)
  작업에 대한 참고자료(PR, 피그마, 슬랙 등)가 있는 경우 링크를 참고 자료에 같이 추가해주세요.
  히스토리나 정책, 특정 기술 등에 대한 이해가 필요한 작업일 때 참고자료가 있다면 리뷰어에게 큰 도움이 됩니다!
-->

## PR 설명

<!-- 해당 PR이 왜 발생했고, 어떤부분에 대한 작업인지 작성해주세요. -->

## 리뷰 포인트

<!--
    리뷰어가 함께 고민해주었으면 하는 내용을 간략하게 기재해주세요.
    커밋 링크가 포함되면, 더욱이 효과적일 거예요!
-->

## Definition of Done (DoD)

<!--
    DOD 란 해당 작업을 완료했다고 간주하기 위해 충족해야 하는 기준을 의미합니다.
    어떤 기능을 위해 어떤 요구사항을 만족하였으며, 어떤 테스트를 수행했는지 등을 명확하게 체크리스트로 기재해 주세요.
    리뷰어 입장에서, 모든 맥락을 파악하기 이전에 작업의 성숙도/완성도를 파악하는 데에 도움이 됩니다.
    만약 계획되거나 연관 작업이나 파생 작업이 존재하는데, 이후로 미뤄지는 경우 TODO -, 사유와 함께 적어주세요.

    ex:
    - [x] 상품 도메인 모델 구조 설계 완료 ( [정책 참고자료](관련 문서 링크) )
    - [x] 상품 재고 차감 로직 유닛/통합 테스트 완료
    - [ ] TODO - 상품 주문 로직 개발 ( 정책 미수립으로 인해 후속 작업에서 진행 )
-->

## 코드 스타일

### Java 코딩 컨벤션

-   표준 Java 네이밍 컨벤션 준수
-   JavaDoc 주석 작성 (필수 대상에 한정)

### JavaDoc 작성 가이드

#### 필수 작성 대상

-   **공통 유틸리티 클래스** (ApiResponse, Error 등)
-   **복잡한 비즈니스 로직** 메서드
-   **외부 API** 인터페이스
-   **라이브러리** 코드

#### 작성하지 않아도 되는 경우

-   **단순한 VO/DTO** 클래스
-   **명확한 변수명** (success, data 등)
-   **Getter/Setter** 메서드
-   **단순한 생성자**

#### JavaDoc 작성 규칙

**클래스 레벨:**

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

**메서드 레벨:**

```java
/**
 * 성공 응답 생성
 */
public static <T> ApiResponse<T> success(T data) {
    // ...
}

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

**필드 레벨 (인라인 주석):**

```java
public class ApiResponse<T> {
    private boolean success;  // 성공 여부
    private T data;          // 응답 데이터
    private Error error;     // 에러 정보
}
```

---

이 가이드라인을 따라 일관된 개발 환경을 유지해주세요.
