# ERD 도면 설명서 (개발용)

본 문서는 전자상거래 플랫폼의 데이터베이스 구조를 설명합니다.  
제약조건(PK, FK, 1:1 등)은 컬럼 옆 **제약** 열에 명시했습니다.

---

## CUSTOMER (고객)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CUSTOMER_ID | PK | 고객 고유식별자 |
| CUSTOMER_NAME |  | 고객명 |
| PASSWORD |  | 비밀번호(단방향 암호화 필요) |
| EMAIL |  | 이메일 주소(양방향 암호화 필요)|
| PHONE |  | 전화번호(양방향 암호화 필요)|
| ADDRESS |  | 주소(양방향 암호화 필요)|
| CUSTOMER_STATUS |  | 활성상태(CODE 참조)|
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## CODE_CATEGORY (코드카테고리)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CATEGORY_CODE | PK | 카테고리 코드 |
| CATEGORY_NAME |  | 카테고리명 |
| CATEGORY_DESCRIPTION |  | 카테고리 설명 |
| ACTIVE_YN |  | 활성여부 (Y, N) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## CODE_ITEM (코드항목)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CATEGORY_CODE | PK, FK | 카테고리 코드 |
| ITEM_CODE | PK | 항목 코드 |
| ITEM_NAME |  | 항목명 |
| ITEM_DESCRIPTION |  | 항목 설명 |
| SORT_ORDER |  | 정렬 순서 |
| ACTIVE_YN |  | 활성여부 (Y, N) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## ADMIN (관리자)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| ADMIN_ID | PK | 관리자 고유식별자 |
| ADMIN_NAME |  | 관리자명 |
| PASSWORD |  | 비밀번호 (단방향 암호화 필요) |
<!-- 개인정보, 부서/직급은 추후 확장... -->
<!-- 
| EMAIL |  | 이메일 주소 (양방향 암호화 필요) |
| PHONE |  | 전화번호 (양방향 암호화 필요) |
| DEPARTMENT |  | 부서 |
| POSITION |  | 직급 | -->
| ADMIN_STATUS |  | 관리자 상태 (CODE 참조) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## ADMIN_ROLE (관리자권한)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| ADMIN_ID | PK, FK | 관리자 ID |
| ROLE_ID | PK, FK | 권한 ID |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## ROLE (권한)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| ROLE_ID | PK | 권한 고유 식별자 |
| ROLE_NAME |  | 권한명 |
| ROLE_DESCRIPTION |  | 권한 설명 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |


## WALLET (잔고) <span style="font-size:0.85em;">(CUSTOMER 와 1:1)</span>
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CUSTOMER_ID | PK, FK | 고객 ID |
| BALANCE |  | 현재 잔고 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## WALLET_HISTORY (잔고내역)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CUSTOMER_ID | PK, FK | 고객 ID |
| AMOUNT |  | 사용액 |
| USE_TYPE |  | 사용 유형 (CODE 참조) |
| AMOUNT_AFTER |  | 이후 잔액 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## CART (장바구니)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CUSTOMER_ID | PK, FK | 고객 ID |
| CART_TYPE | PK | 장바구니 유형 (CODE 참조) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## COUPON (쿠폰)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| COUPON_ID | PK | 쿠폰 ID |
| COUPON_NAME |  | 쿠폰명 |
| COUPON_TYPE |  | 쿠폰 유형 (CODE 참조) |
| COUPON_ATTRIBUE1 |  | (추가 속성) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## CUSTOMER_COUPON (고객소유쿠폰)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CUSTOMER_ID | PK, FK | 고객 ID |
| COUPON_ID | PK, FK | 쿠폰 ID |
| COUPON_STATUS |  | 상태 (CODE 참조) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## CUSTOMER_CART_ITEM (장바구니항목)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CUSTOMER_ID | PK, FK | 고객 ID |
| CART_TYPE | PK, FK | 장바구니 유형 |
| PRODUCT_ID | PK, FK | 상품 ID |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## PRODUCT (상품)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| PRODUCT_ID | PK | 상품 ID |
| PRODUCT_NAME |  | 상품명 |
| PRODUCT_PRICE |  | 상품가격 |
| PRODUCT_STOCK |  | 재고수량 |
| PRODUCT_STATUS |  | 상품 상태 (CODE 참조) | 
| PRODUCT_DESCRIPTION |  | 상품 설명 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

<!--하나의 상품이 여러 유형 가질 수 있음 - 상품N:N:유형 해결 위한 중간테이블 추가-->
## CATEGORY_PRODUCT (카테고리-상품연결)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CATEGORY_ID | PK, FK | 카테고리 ID |
| PRODUCT_ID | PK, FK | 상품 ID |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

<!--상품유형 마스터 테이블로 변경-->
## CATEGORY (상품유형)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| CATEGORY_ID | PK | 카테고리 ID |
| CATEGORY_NAME |  | 카테고리명 |
| CATEGORY_DESCRIPTION |  | 설명 |
| PARENT_CATEGORY_ID | FK | 상위 카테고리 ID (자기참조) |
| CATEGORY_LEVEL |  | 카테고리 레벨 (1,2,3...) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

<!-- 주문 테이블에 결제도 통합-->
## ORDER (주문)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| ORDER_NO | PK | 주문번호 |
| CUSTOMER_ID | FK | 고객 ID |
| ORDER_STATUS |  | 주문 상태 (CODE 참조) |
| TOTAL_AMOUNT |  | 총 결제 금액 |
| PAYMENT_METHOD |  | 결제 수단 (CODE 참조) |
| PAYMENT_STATUS |  | 결제 상태 (CODE 참조) |
| PAYMENT_DATE |  | 결제 일시 |
| CANCEL_DATE |  | 취소 일시 |
| DELIVERY_NAME |  | 배송지 수령인명 |
| DELIVERY_PHONE |  | 배송지 연락처 |
| DELIVERY_ADDRESS |  | 배송지 주소 |
| DELIVERY_ZIPCODE |  | 배송지 우편번호 |
| DELIVERY_DESCRIPTION |  | 배송 설명 |
| ORDER_DATE |  | 주문 일시 |
| DELIVERY_DATE |  | 배송 완료 일시 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

<!--상품1:N이미지 관계로 변경, PK,FK개선-->
## PRODUCT_IMAGE (상품이미지)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| PRODUCT_IMAGE_ID | PK | 상품이미지 ID |
| PRODUCT_ID | FK | 상품 ID |
| IMAGE_URL |  | 이미지 URL |
| IMAGE_DESCRIPTION |  | 이미지 설명 |
| DISPLAY_ORDER |  | 표시 순서 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

<!--고객 민원 및 오류 확인 용 테이블로 변경-->
## ORDER_HISTORY (주문내역)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| HISTORY_ID | PK | 이력 ID |
| ORDER_NO | FK | 주문번호 |
| CUSTOMER_ID | FK | 고객 ID |
| PRODUCT_ID | FK | 상품 ID |
| ACTION_TYPE |  | 액션 유형 (ORDER, CANCEL, REFUND 등)  (CODE 참조) |
| QUANTITY |  | 수량 |
| PRICE |  | 가격 |
| STATUS_BEFORE |  | 액션 이전 상태  (CODE 참조)|
| STATUS_AFTER |  | 액션 이후 상태  (CODE 참조)|
| REASON |  | 사유 (취소/환불 사유 등) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## ORDER_ITEM (주문항목)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| ORDER_NO | PK, FK | 주문번호 |
| PRODUCT_ID | PK, FK | 상품 ID |
| QUANTITY |  | 주문 수량 |
| ORDER_PRICE |  | 단가 (주문 시점 가격) |
| TOTAL_ORDER_PRICE |  | 총 가격 (QUANTITY × ORDER_PRICE) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

---

## 컬럼 명명 규칙

### **YN 접미사**
- **용도**: 단순한 Y/N 값 (Yes/No)
- **데이터 타입**: VARCHAR(1) 또는 CHAR(1)
- **값**: 'Y', 'N'
- **예시**: ACTIVE_YN, DEL_YN
- **특징**: CODE_ITEM 테이블 참조 없이 직접 값 저장

### **STATUS 접미사**
- **용도**: 복잡한 상태값 (상태 코드)
- **데이터 타입**: VARCHAR(10) 또는 CHAR(10)
- **값**: CODE_ITEM 테이블의 ITEM_CODE 참조
- **예시**: ORDER_STATUS, PAYMENT_STATUS, PRODUCT_STATUS
- **특징**: CODE_ITEM 테이블과 조인하여 상태명 조회

### **명명 규칙 적용 예시**
```sql
-- YN 접미사 (단순 Y/N 값)
ACTIVE_YN: 'Y' (활성), 'N' (비활성)
DEL_YN: 'Y' (삭제됨), 'N' (정상)

-- STATUS 접미사 (CODE_ITEM 참조)
ORDER_STATUS: '1' (결제중), '2' (결제완료), '3' (상품준비중)
PAYMENT_STATUS: '1' (대기), '2' (완료), '3' (실패)
```

---

**공통 필드:** 모든 테이블에는 FIRST_INPUT_DTTM, FIRST_INPUT_ID, LAST_INPUT_DTTM, LAST_INPUT_ID가 존재하여 데이터 이력 추적에 사용됩니다.
