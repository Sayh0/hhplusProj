package kr.hhplus.be.server.order.controller;

import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.order.application.OrderUseCase;
import kr.hhplus.be.server.order.application.dto.CreateOrderCommand;
import kr.hhplus.be.server.order.application.dto.OrderItemRequest;
import kr.hhplus.be.server.order.controller.dto.CreateOrderRequest;
import kr.hhplus.be.server.order.controller.dto.OrderItemDto;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.vo.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 주문 관련 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;

    /**
     * 주문 및 결제 API (API 명세서 6.1)
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        try {
            // Controller DTO를 Application DTO로 변환
            CreateOrderCommand command = toCreateOrderCommand(request);

            // 주문 생성 및 결제 처리
            Order order = orderUseCase.createOrder(command);

            // OrderResponse로 변환
            OrderResponse response = toOrderResponse(order);

            return ResponseEntity.ok(ApiResponse.success(response));

        } catch (RuntimeException e) {
            // 비즈니스 예외 처리
            if (e.getMessage().contains("재고")) {
                return ResponseEntity.status(409)
                        .body(ApiResponse.error("INSUFFICIENT_STOCK", e.getMessage()));
            } else if (e.getMessage().contains("잔액")) {
                return ResponseEntity.status(409)
                        .body(ApiResponse.error("INSUFFICIENT_BALANCE", e.getMessage()));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("BAD_REQUEST", e.getMessage()));
            }
        }
    }

    /**
     * CreateOrderRequest를 CreateOrderCommand로 변환
     */
    private CreateOrderCommand toCreateOrderCommand(CreateOrderRequest request) {
        List<OrderItemRequest> orderItems = request.getItems().stream()
                .map(this::toOrderItemRequest)
                .collect(Collectors.toList());

        return new CreateOrderCommand(request.getUserId(), orderItems);
    }

    /**
     * Controller OrderItemDto를 Application OrderItemRequest로 변환
     */
    private OrderItemRequest toOrderItemRequest(OrderItemDto request) {
        // TODO: 실제로는 상품 정보를 조회해서 name과 unitPrice를 가져와야 함
        // 현재는 임시로 하드코딩
        String productName = getProductName(request.getProductId());
        Long unitPrice = getProductPrice(request.getProductId());

        return new OrderItemRequest(
                request.getProductId(),
                productName,
                request.getQuantity(),
                unitPrice);
    }

    /**
     * Order를 OrderResponse로 변환
     */
    private OrderResponse toOrderResponse(Order order) {
        // TODO: 실제로는 쿠폰 할인 금액을 계산해야 함
        long totalAmount = order.getTotalAmount();
        long discountAmount = 50000L; // 임시 값
        long finalAmount = totalAmount - discountAmount;

        return new OrderResponse(
                order.getId(),
                totalAmount,
                discountAmount,
                finalAmount,
                order.getStatus().name(),
                order.getCreatedAt());
    }

    /**
     * 상품명 조회 (임시 구현)
     */
    private String getProductName(Long productId) {
        // TODO: 실제로는 ProductService를 통해 조회
        return switch (productId.intValue()) {
            case 1 -> "스마트폰";
            case 3 -> "노트북";
            default -> "상품" + productId;
        };
    }

    /**
     * 상품 가격 조회 (임시 구현)
     */
    private Long getProductPrice(Long productId) {
        // TODO: 실제로는 ProductService를 통해 조회
        return switch (productId.intValue()) {
            case 1 -> 500000L;
            case 3 -> 1000000L;
            default -> 100000L;
        };
    }

    /**
     * 주문 응답 DTO
     */
    public static class OrderResponse {
        private final Long orderId;
        private final Long totalAmount;
        private final Long discountAmount;
        private final Long finalAmount;
        private final String status;
        private final LocalDateTime createdAt;

        public OrderResponse(Long orderId, Long totalAmount, Long discountAmount,
                Long finalAmount, String status, LocalDateTime createdAt) {
            this.orderId = orderId;
            this.totalAmount = totalAmount;
            this.discountAmount = discountAmount;
            this.finalAmount = finalAmount;
            this.status = status;
            this.createdAt = createdAt;
        }

        // Getters
        public Long getOrderId() {
            return orderId;
        }

        public Long getTotalAmount() {
            return totalAmount;
        }

        public Long getDiscountAmount() {
            return discountAmount;
        }

        public Long getFinalAmount() {
            return finalAmount;
        }

        public String getStatus() {
            return status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
