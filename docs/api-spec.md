
# e-커머스 상품 주문 서비스 시나리오 API 명세서

## 개요
"e-커머스 상품 주문 서비스 시나리오"의 REST API 명세서입니다.

### Base URL
http://localhost:8080/api/v1

### 공통 응답 형식
```json
{
  "success": true,
  "data": {},
  "error": {
    "code": "ERROR_CODE",
    "message": "에러 메시지"
  },
  "timestamp": "2025-08-29T01:57:00Z"
}
```

### HTTP 상태 코드
| 상태 코드 | 설명 |
|-----------|------|
| 200 | 성공 |
| 201 | 생성 성공 |
| 400 | 잘못된 요청 |
| 404 | 리소스 없음 |
| 409 | 충돌 (재고 부족, 잔액 부족 등) |
| 500 | 서버 오류 |

---

## API 목차 (Index)

### 1. 잔액 관리 API
- [1.1 잔액 충전](#1-잔액-충전)
- [1.2 잔액 조회](#1-2-잔액-조회)

### 2. 상품 관리 API
- [2.1 상품 목록 조회](#2-1-상품-목록-조회)
- [2.2 상품 상세 조회](#2-2-상품-상세-조회)

### 3. 주문 관리 API
- [3.1 주문 생성](#3-1-주문-생성)
- [3.2 주문 조회](#3-2-주문-조회)
- [3.3 주문 취소](#3-3-주문-취소)

---

## 1. 잔액 관리 API

### 1.1 잔액 충전
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

**Error Cases**
- `400 INVALID_AMOUNT`: 충전 금액이 0 이하인 경우
- `404 USER_NOT_FOUND`: 존재하지 않는 사용자

### 1.2 잔액 조회
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
    "balance": 15000
  }
}
```

---

## 2. 상품 관리 API

### 2.1 상품 목록 조회
전체 상품 목록을 조회합니다.

```http
GET /products
```

**Query Parameters**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| page | int | N | 페이지 번호 (기본값: 0) |
| size | int | N | 페이지 크기 (기본값: 20) |

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
        "description": "최신 스마트폰",
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

### 2.2 상품 상세 조회
특정 상품의 상세 정보를 조회합니다.

```http
GET /products/{productId}
```

**Response**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "스마트폰",
    "price": 500000,
    "stock": 10,
    "description": "최신 스마트폰",
    "createdAt": "2025-08-29T01:00:00Z"
  }
}
```

---

## 3. 쿠폰 관리 API

### 3.1 선착순 쿠폰 발급
선착순으로 쿠폰을 발급받습니다.

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
- `409 COUPON_SOLD_OUT`: 쿠폰이 모두 소진된 경우
- `409 ALREADY_ISSUED`: 이미 발급받은 사용자인 경우

### 3.2 보유 쿠폰 목록 조회
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

## 4. 주문 관리 API

### 4.1 주문하기
상품을 주문하고 결제를 처리합니다.

```http
POST /orders
```

**Request**
```json
{
  "userId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ],
  "couponId": 123
}
```

**Response**
```json
{
  "success": true,
  "data": {
    "orderId": 456,
    "userId": 1,
    "totalAmount": 1000000,
    "discountAmount": 5000,
    "finalAmount": 995000,
    "status": "COMPLETED",
    "items": [
      {
        "productId": 1,
        "productName": "스마트폰",
        "quantity": 2,
        "unitPrice": 500000,
        "totalPrice": 1000000
      }
    ],
    "createdAt": "2025-08-29T01:57:00Z"
  }
}
```

**Error Cases**
- `409 INSUFFICIENT_BALANCE`: 잔액 부족
- `409 INSUFFICIENT_STOCK`: 재고 부족
- `400 INVALID_COUPON`: 유효하지 않은 쿠폰
- `404 PRODUCT_NOT_FOUND`: 존재하지 않는 상품

### 4.2 주문 내역 조회
사용자의 주문 내역을 조회합니다.

```http
GET /users/{userId}/orders
```

**Response**
```json
{
  "success": true,
  "data": {
    "orders": [
      {
        "orderId": 456,
        "totalAmount": 995000,
        "status": "COMPLETED",
        "createdAt": "2025-08-29T01:57:00Z"
      }
    ]
  }
}
```

---

## 5. 통계 API

### 5.1 인기 상품 조회
최근 3일간 가장 많이 팔린 상위 5개 상품을 조회합니다.

```http
GET /products/popular
```

**Query Parameters**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| days | int | N | 조회 기간 (기본값: 3일) |
| limit | int | N | 조회 개수 (기본값: 5개) |

**Response**
```json
{
  "success": true,
  "data": {
    "period": {
      "startDate": "2025-08-26T00:00:00Z",
      "endDate": "2025-08-29T23:59:59Z"
    },
    "products": [
      {
        "rank": 1,
        "productId": 1,
        "productName": "스마트폰",
        "totalSold": 150,
        "totalRevenue": 75000000
      },
      {
        "rank": 2,
        "productId": 2,
        "productName": "노트북",
        "totalSold": 80,
        "totalRevenue": 80000000
      }
    ]
  }
}
```

---

## 에러 코드 목록

| 에러 코드 | HTTP 상태 | 설명 |
|-----------|----------|------|
| `USER_NOT_FOUND` | 404 | 사용자를 찾을 수 없음 |
| `PRODUCT_NOT_FOUND` | 404 | 상품을 찾을 수 없음 |
| `INSUFFICIENT_BALANCE` | 409 | 잔액 부족 |
| `INSUFFICIENT_STOCK` | 409 | 재고 부족 |
| `INVALID_AMOUNT` | 400 | 유효하지 않은 금액 |
| `COUPON_SOLD_OUT` | 409 | 쿠폰 소진 |
| `ALREADY_ISSUED` | 409 | 이미 발급된 쿠폰 |
| `INVALID_COUPON` | 400 | 유효하지 않은 쿠폰 |
| `ORDER_NOT_FOUND` | 404 | 주문을 찾을 수 없음 |

---

## 인증 (추후 구현)
현재는 인증 없이 userId를 직접 전달하는 방식이지만, 추후 JWT 토큰 기반 인증으로 변경 예정입니다.

```http
Authorization: Bearer {jwt-token}
```
