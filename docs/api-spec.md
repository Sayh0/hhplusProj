# e-커머스 상품 주문 서비스 시나리오 API 명세서

## 개요

"e-커머스 상품 주문 서비스 시나리오"의 REST API 명세서입니다.

### Base URL

```
http://localhost:8080/api/v1
```

### 공통 응답 형식

```json
{
    "success": true,
    "data": {},
    "error": {
        "code": "ERROR_CODE",
        "message": "에러 메시지"
    },
    "timestamp": "YYYY-MM-DD HH:mm:SS"
}
```

### HTTP 상태 코드

| 상태 코드 | 설명                           |
| --------- | ------------------------------ |
| 200       | 성공                           |
| 201       | 생성 성공                      |
| 400       | 잘못된 요청                    |
| 401       | 인증 필요                      |
| 403       | 권한 없음                      |
| 404       | 리소스 없음                    |
| 409       | 충돌 (재고 부족, 잔액 부족 등) |
| 500       | 서버 오류                      |

---

## API 목록

### 1. 인증 API

-   [1.1 로그인](#11-로그인)

### 2. 잔액 관리 API

-   [2.1 잔액 충전](#21-잔액-충전)
-   [2.2 잔액 조회 (사용자용)](#22-잔액-조회-사용자용)
-   [2.3 잔액 조회 (관리자용)](#23-잔액-조회-관리자용)

### 3. 상품 관리 API

-   [3.1 상품 목록 조회](#31-상품-목록-조회)
-   [3.2 상품 등록 (관리자 전용)](#32-상품-등록-관리자-전용)
-   [3.3 상품 수정 (관리자 전용)](#33-상품-수정-관리자-전용)
-   [3.4 상품 논리적 삭제 (관리자 전용)](#34-상품-논리적-삭제-관리자-전용)
    <!-- 물리적 삭제는 위험하므로 API로 제공하지 않음 - 삭제가 필요한 경우 수작업으로 직접 처리. -->
    <!-- - [3.5 상품 물리적 삭제 (관리자 전용)](#35-상품-물리적-삭제-관리자-전용) -->
-   [3.6 삭제된 상품 복구 (관리자 전용)](#36-삭제된-상품-복구-관리자-전용)

### 4. 카테고리 관리 API

-   [4.1 카테고리 목록 조회](#41-카테고리-목록-조회)

### 5. 장바구니 API

-   [5.1 장바구니 조회](#51-장바구니-조회)
-   [5.2 장바구니에 상품 추가](#52-장바구니에-상품-추가)

### 6. 주문/결제 API

-   [6.1 주문 및 결제](#61-주문-및-결제)

### 7. 쿠폰 관리 API

-   [7.1 선착순 쿠폰 발급](#71-선착순-쿠폰-발급)
-   [7.2 보유 쿠폰 목록 조회](#72-보유-쿠폰-목록-조회)

### 8. 인기 상품 조회 API

-   [8.1 최근 3일간 상위 5개 상품 조회](#81-최근-3일간-상위-5개-상품-조회)

---

## 1. 인증 API

### 1.1 로그인

사용자 로그인 및 토큰 발급

```http
POST /auth/login
```

**Request**

```json
{
    "username": "user1",
    "password": "password123"
}
```

**Response**

```json
{
    "success": true,
    "data": {
        "token": "eyJhbGci..."
    }
}
```

---

## 2. 잔액 관리 API

### 2.1 잔액 충전

사용자의 잔액을 충전합니다.

```http
POST /users/{userId}/balance/charge
```

**Request**

```json
{
    "amount": 10000
}
```

**Response**

```json
{
    "success": true,
    "data": {
        "userId": 1,
        "balance": 15000,
        "chargedAmount": 10000
    }
}
```

### 2.2 잔액 조회 (사용자용)

사용자의 현재 잔액을 조회합니다.

```http
GET /users/{userId}/balance
```

**Response**

```json
{
    "success": true,
    "data": {
        "userId": 1,
        "username": "user1",
        "balance": 15000
    }
}
```

### 2.3 잔액 조회 (관리자용)

관리자가 모든 사용자의 잔액을 조회합니다.

```http
GET /admin/users/{userId}/balance
```

**Response**

```json
{
    "success": true,
    "data": {
        "userId": 1,
        "username": "user1",
        "balance": 15000
        // TODO: 관리자용 상제 정보 추가 예정
        // "totalCharged": 50000,
        // "totalUsed": 35000,
        // "lastChargedAt": "2025-08-29T02:00:00Z",
        // "lastUsedAt": "2025-08-29T01:30:00Z",
        // "status": "ACTIVE"
    }
}
```

---

## 3. 상품 관리 API

### 3.1 상품 목록 조회

전체 상품 목록을 조회합니다. 고객/관리자 모두 이용 가능합니다.

```http
GET /products
```

**Query Parameters**
| 파라미터 | 타입 | 필수여부 | 설명 |
|----------|------|------|------|
| page | int | N | 페이지 번호 (기본값: 0) |
| size | int | N | 페이지 크기 (기본값: 20) |
| status | string | N | 상품 활성화여부 (기본값:"ACTIVE") |
| category | int | N | 카테고리 ID 검색 조회조건 |
| keyword | string | N | 상품명 검색 조회조건 |

**Response**

```json
{
    "success": true,
    "data": {
        "products": [
            {
                "id": 1,
                "name": "스마트폰",
                "price": 500000,
                "stock": 10,
                "categoryId": 1,
                "categoryName": "전자제품",
                // TODO : 이미지 파일 추가 시 구현...
                // "images": [
                //   {
                //     "id": 1,
                //     "url": "/images/products/1/main.jpg",
                //     "isMain": true
                //   }
                // ],
                "createdAt": "2025-08-29T01:00:00Z"
            }
        ],
        "pagination": {
            "page": 0,
            "size": 20,
            "totalElements": 50,
            "totalPages": 3
        }
    }
}
```

### 3.2 상품 등록 (관리자 전용)

```http
POST /admin/products
```

**Request**

```json
{
    "name": "노트북",
    "price": 1000000,
    "categoryId": 1,
    "stock": 20,
    "description": "고성능 노트북"
}
```

**Response**

```json
{
    "success": true,
    "data": {
        "productId": 2
    }
}
```

### 3.3 상품 수정 (관리자 전용)

상품 정보를 수정합니다.

```http
PUT /admin/products/{productId}
```

**Request**

```json
{
    "name": "고성능 노트북 Pro",
    "price": 1200000,
    "categoryId": 1,
    "stock": 25,
    "description": "업그레이드된 고성능 노트북"
}
```

**Response**

```json
{
    "success": true,
    "data": {
        "productId": 2,
        "updatedAt": "2025-08-29T03:00:00Z"
    }
}
```

### 3.4 상품 논리적 삭제 (관리자 전용)

상품을 논리적으로 삭제합니다. 데이터는 유지되지만 판매가 중단됩니다.

```http
PATCH /admin/products/{productId}/soft-delete
```

**Response**

```json
{
    "success": true,
    "data": {
        "productId": 2,
        "status": "DELETED",
        "deletedAt": "2025-08-29T03:00:00Z"
    }
}
```

<!--
### 3.5 상품 물리적 삭제 (관리자 전용)
상품을 완전히 삭제합니다. 복구가 불가능합니다.

```http
DELETE /admin/products/{productId}
```

**Response**
```json
{
  "success": true,
  "data": {
    "message": "상품이 완전히 삭제되었습니다."
  }
}
```

**주의사항**: 물리적 삭제는 위험하므로 API로 제공하지 않습니다.
삭제가 필요한 경우 수작업으로 데이터베이스에서 직접 처리합니다.
-->

### 3.6 삭제된 상품 복구 (관리자 전용)

논리적으로 삭제된 상품을 복구합니다.

```http
PATCH /admin/products/{productId}/restore
```

**Response**

```json
{
    "success": true,
    "data": {
        "productId": 2,
        "status": "ACTIVE",
        "restoredAt": "2025-08-29T03:00:00Z"
    }
}
```

---

## 4. 카테고리 관리 API

### 4.1 카테고리 목록 조회

```http
GET /categories
```

**Response**

```json
{
    "success": true,
    "data": {
        "categories": [
            {
                "id": 1,
                "name": "전자제품"
            }
        ]
    }
}
```

---

## 5. 장바구니 API

### 5.1 장바구니 조회

```http
GET /cart
```

**Response**

```json
{
    "success": true,
    "data": {
        "items": [
            {
                "itemId": 1,
                "productId": 1,
                "productName": "스마트폰",
                "quantity": 2,
                "unitPrice": 500000
            }
        ]
    }
}
```

### 5.2 장바구니에 상품 추가

```http
POST /cart/items
```

**Request**

```json
{
    "productId": 1,
    "quantity": 2
}
```

**Response**

```json
{
    "success": true
}
```

---

## 6. 주문/결제 API

### 6.1 주문 및 결제

```http
POST /orders
```

**Request**

```json
{
    "userId": 1,
    "items": [
        { "productId": 1, "quantity": 2 },
        { "productId": 3, "quantity": 1 }
    ],
    "couponId": 123
}
```

**Response**

```json
{
    "success": true,
    "data": {
        "orderId": 789,
        "totalAmount": 2000000,
        "discountAmount": 50000,
        "finalAmount": 1950000,
        "status": "COMPLETED",
        "createdAt": "2025-08-29T02:27:00Z"
    }
}
```

---

## 7. 쿠폰 관리 API

### 7.1 선착순 쿠폰 발급

사용자가 선착순으로 할인 쿠폰을 발급받습니다.

```http
POST /coupons/{couponId}/issue
```

**Request**

```json
{
    "userId": 1
}
```

**Response**

```json
{
    "success": true,
    "data": {
        "userCouponId": 123,
        "couponId": 1,
        "userId": 1,
        "discountAmount": 5000,
        "expiresAt": "2025-09-29T01:57:00Z",
        "issuedAt": "2025-08-29T01:57:00Z"
    }
}
```

**Error Cases**

-   `409 COUPON_SOLD_OUT`: 쿠폰이 모두 소진된 경우
-   `409 ALREADY_ISSUED`: 이미 발급받은 사용자인 경우

### 7.2 보유 쿠폰 목록 조회

사용자가 보유한 쿠폰 목록을 조회합니다.

```http
GET /users/{userId}/coupons
```

**Response**

```json
{
    "success": true,
    "data": {
        "coupons": [
            {
                "userCouponId": 123,
                "couponId": 1,
                "discountAmount": 5000,
                "status": "AVAILABLE",
                "expiresAt": "2025-09-29T01:57:00Z",
                "issuedAt": "2025-08-29T01:57:00Z"
            }
        ]
    }
}
```

---

## 8. 인기 상품 조회 API

### 8.1 최근 3일간 상위 5개 상품 조회

```http
GET /products/popular
```

**Response**

```json
{
    "success": true,
    "data": {
        "products": [
            {
                "productId": 1,
                "productName": "스마트폰",
                "totalSold": 150,
                "totalRevenue": 75000000
            }
        ]
    }
}
```

---

## 에러 코드 목록

| 에러 코드              | HTTP 상태 | 설명                    |
| ---------------------- | --------- | ----------------------- |
| `USER_NOT_FOUND`       | 404       | 사용자를 찾을 수 없음   |
| `PRODUCT_NOT_FOUND`    | 404       | 상품을 찾을 수 없음     |
| `INSUFFICIENT_BALANCE` | 409       | 잔액 부족               |
| `INSUFFICIENT_STOCK`   | 409       | 재고 부족               |
| `UNAUTHORIZED`         | 401       | 인증이 필요함           |
| `FORBIDDEN`            | 403       | 권한이 없음             |
| `CART_EMPTY`           | 400       | 장바구니가 비어 있음    |
| `CATEGORY_NOT_FOUND`   | 404       | 카테고리를 찾을 수 없음 |
| `INVALID_AMOUNT`       | 400       | 유효하지 않은 금액      |
| `COUPON_SOLD_OUT`      | 409       | 쿠폰 소진               |
| `ALREADY_ISSUED`       | 409       | 이미 발급된 쿠폰        |
| `INVALID_COUPON`       | 400       | 유효하지 않은 쿠폰      |
| `ORDER_NOT_FOUND`      | 404       | 주문을 찾을 수 없음     |

---

## 인증 및 권한

모든 인증이 필요한 API는 `Authorization: Bearer {jwt-token}` 헤더를 사용합니다.
관리자 권한이 필요한 경우 별도 권한 체크가 적용됩니다.
