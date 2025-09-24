# ERD 도면 설명서 (개발용)

본 문서는 전자상거래 플랫폼의 데이터베이스 구조를 설명합니다.  
제약조건(PK, FK, 1:1 등)은 컬럼 옆 **제약** 열에 명시했습니다.

---

## USER (사용자마스터테이블)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| USER_ID | PK | 사용자 고유 식별자 |
| USER_NAME |  | 사용자명 |
| PASSWORD |  | 비밀번호 |
| EMAIL |  | 이메일 주소 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## USER_ROLE (사용자소유권한)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| USER_ID | PK, FK | 사용자 ID |
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
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## WALLET (잔고) <span style="font-size:0.85em;">(USER 와 1:1)</span>
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| USER_ID | PK, FK | 사용자 ID |
| BALANCE |  | 현재 잔고 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## WALLET_HISTORY (잔고내역)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| USER_ID | PK, FK | 사용자 ID |
| AMOUNT |  | 사용액 |
| USE_TYPE |  | 사용 유형 |
| AMOUNT_AFTER |  | 이후 잔액 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## CART (장바구니)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| USER_ID | PK, FK | 사용자 ID |
| CART_TYPE | PK | 장바구니 유형 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## COUPON (쿠폰)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| COUPON_ID | PK | 쿠폰 ID |
| COUPON_NAME |  | 쿠폰명 |
| COUPON_TYPE |  | 쿠폰 유형 |
| COUPON_ATTRIBUE1 |  | (추가 속성) |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## USER_COUPON (사용자소유쿠폰)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| USER_ID | PK, FK | 사용자 ID |
| COUPON_ID | PK, FK | 쿠폰 ID |
| STATUS |  | 사용 상태 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## USER_CART_ITEM (장바구니항목)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| USER_ID | PK, FK | 사용자 ID |
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
| PRODUCT_STATUS |  | 활성/비활성 |
| PRODUCT_DESCRIPTION |  | 상품 설명 |
<!--DELETED 상태여부 추가 -->
| DEL_YN |  | (논리적)삭제여부 |
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
| USER_ID | FK | 사용자 ID |
| ORDER_STATUS |  | 주문 상태 (PAYMENT_PENDING결제중, PAYMENT_COMPLETED결제완료, PREPARING상품준비중, SHIPPING배송중, DELIVERED배송완료) |
| TOTAL_AMOUNT |  | 총 결제 금액 |
| PAYMENT_METHOD |  | 결제 수단 (CARD카드, BANK_TRANSFER계좌이체, ETC기타) |
| PAYMENT_STATUS |  | 결제 상태 (PENDING대기, COMPLETED완료, FAILED실패, CANCELLED취소됨) |
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
| USER_ID | FK | 사용자 ID |
| PRODUCT_ID | FK | 상품 ID |
| ACTION_TYPE |  | 액션 유형 (ORDER, CANCEL, REFUND 등) |
| QUANTITY |  | 수량 |
| PRICE |  | 가격 |
| STATUS_BEFORE |  | 이전 상태 |
| STATUS_AFTER |  | 이후 상태 |
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
**공통 필드:** 모든 테이블에는 FIRST_INPUT_DTTM, FIRST_INPUT_ID, LAST_INPUT_DTTM, LAST_INPUT_ID가 존재하여 데이터 이력 추적에 사용됩니다.
