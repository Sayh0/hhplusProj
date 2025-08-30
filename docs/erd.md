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
| CATEGORY_ID | FK | 카테고리 ID |
| PRODUCT_NAME |  | 상품명 |
| PRODUCT_PRICE |  | 상품가격 |
| PRODUCT_STOCK |  | 재고수량 |
| PRODUCT_STATUS |  | 활성/비활성 |
| PRODUCT_DESCRIPTION |  | 상품 설명 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## CATEGORY (상품유형)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| PRODUCT_ID | PK, FK | 상품 ID |
| CATEGORY_ID | PK, FK | 카테고리 ID |
| CATEGORY_NAME |  | 카테고리명 |
| CATEGORY_DESCRIPTION |  | 설명 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## ORDER (주문)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| ORDER_NO | PK | 주문번호 |
| USER_ID | FK | 사용자 ID |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## PAYMENT (결제)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| PAYMENT_ID | PK | 결제 ID |
| ORDER_NO | FK | 주문번호 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## PRODUCT_IMAGE (상품이미지)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| PRODUCT_ID | PK, FK | 상품 ID |
| CATEGORY_ID | PK, FK | 카테고리 ID |
| URL |  | 이미지 URL |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## PRODUCT_SALE_HISTORY (판매내역-통계용)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| PRODUCT_ID | PK, FK | 상품 ID |
| CATEGORY_ID | PK, FK | 카테고리 ID |
| SOLD_QUANTITY |  | 판매 수량 |
| SALES_DATE |  | 판매 일자 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

## ORDER_ITEM (주문항목)
| 컬럼명 | 제약 | 설명 |
|--------|------|------|
| ORDER_NO | PK, FK | 주문번호 |
| USER_ID | PK, FK | 사용자 ID |
| PRODUCT_ID | FK | 상품 ID |
| QUANTITY |  | 주문 수량 |
| FIRST_INPUT_DTTM |  | 최초 입력 일시 |
| FIRST_INPUT_ID |  | 최초 입력자 ID |
| LAST_INPUT_DTTM |  | 최종 수정 일시 |
| LAST_INPUT_ID |  | 최종 수정자 ID |

---
**공통 필드:** 모든 테이블에는 FIRST_INPUT_DTTM, FIRST_INPUT_ID, LAST_INPUT_DTTM, LAST_INPUT_ID가 존재하여 데이터 이력 추적에 사용됩니다.
