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
        // Controller DTO를 Application DTO로 변환
        CreateOrderCommand command = toCreateOrderCommand(request);

        // 주문 생성 및 결제 처리
        Order order = orderUseCase.createOrder(command);

        // OrderResponse로 변환
        OrderResponse response = toOrderResponse(order);

        return ResponseEntity.ok(ApiResponse.success(response));
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
        // 상품 정보는 Application 레이어에서 조회하도록 위임
        // 임시값으로 생성하고 Application 레이어에서 실제 값으로 교체
        return new OrderItemRequest(
                request.getProductId(),
                "임시상품", // Application 레이어에서 실제 상품명으로 교체
                request.getQuantity(),
                0L // Application 레이어에서 실제 가격으로 교체
        );
    }

    /**
     * Order를 OrderResponse로 변환
     */
    private OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt());
    }

    /**
     * 주문 응답 DTO
     */
    public static class OrderResponse {
        private final Long orderId;
        private final Long totalAmount;
        private final String status;
        private final LocalDateTime createdAt;

        public OrderResponse(Long orderId, Long totalAmount, String status, LocalDateTime createdAt) {
            this.orderId = orderId;
            this.totalAmount = totalAmount;
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

        public String getStatus() {
            return status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
