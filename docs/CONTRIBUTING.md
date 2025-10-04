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
- `feature/product-controller-tests` (일반 기능 구현)
- `feedback/pr-3-erd-table-structure` (PR 피드백 반영)
- `fix/wallet-history-pk-issue` (일반 버그 수정)

**❌ 잘못된 예시:**
- `Feature/ProductControllerTests` (대문자 사용)
- `feature_product_controller_tests` (언더스코어 사용)
- `feature/pr-3-product-tests` (일반 기능인데 PR 번호 포함)

## 커밋 메시지 컨벤션

### 기본 구조
```
{타입}: {제목}

{본문 (선택사항)}

{푸터 (선택사항)}
```

### 커밋 타입

- **feat**: 새로운 기능 추가
- **fix**: 버그 수정
- **docs**: 문서 수정
- **style**: 코드 포맷팅, 세미콜론 누락 등
- **refactor**: 코드 리팩토링
- **test**: 테스트 코드 추가/수정
- **chore**: 빌드 업무 수정, 패키지 매니저 설정 등

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
```markdown
## 변경 사항
- 변경된 내용을 명시

## 리뷰 포인트
- 리뷰어가 중점적으로 봐야 할 부분

## 테스트
- [ ] 단위 테스트 통과
- [ ] 통합 테스트 통과
- [ ] 수동 테스트 완료

## 관련 이슈
- Closes #{이슈번호}
```

## 코드 스타일

### Java 코딩 컨벤션
- JavaDoc 주석 사용 필수
- 클래스, 메서드, 필드에 대한 설명 포함
- 표준 Java 네이밍 컨벤션 준수

### 예시
```java
/**
 * 상품 정보를 관리하는 서비스 클래스
 * 
 * @author 개발자명
 * @since 2024-12-29
 */
public class ProductService {
    
    /**
     * 상품 ID로 상품 정보를 조회합니다.
     * 
     * @param productId 조회할 상품 ID
     * @return 상품 정보 (없으면 null)
     */
    public ProductVo getProductById(Long productId) {
        // 구현
    }
}
```

---

이 가이드라인을 따라 일관된 개발 환경을 유지해주세요.
