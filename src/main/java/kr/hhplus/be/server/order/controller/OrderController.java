package kr.hhplus.be.server.order.controller;

import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.order.application.OrderUseCase;
import kr.hhplus.be.server.order.application.dto.CreateOrderCommand;
import kr.hhplus.be.server.order.domain.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody CreateOrderCommand request) {
        // 주문 생성 및 결제 처리
        Order order = orderUseCase.createOrder(request);

        return ResponseEntity.ok(ApiResponse.success(order));
    }

}
