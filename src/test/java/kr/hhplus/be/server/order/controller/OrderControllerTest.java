package kr.hhplus.be.server.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.common.exception.InsufficientBalanceException;
import kr.hhplus.be.server.common.exception.InsufficientStockException;
import kr.hhplus.be.server.order.application.OrderUseCase;
import kr.hhplus.be.server.order.application.dto.CreateOrderCommand;
import kr.hhplus.be.server.order.application.dto.OrderItemRequest;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderItem;
import kr.hhplus.be.server.order.domain.vo.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderUseCase orderUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("주문 및 결제 API 테스트 - 성공")
    void shouldCreateOrderAndPaymentSuccess() throws Exception {
        // Given
        CreateOrderCommand request = new CreateOrderCommand(
                1L,
                List.of(
                        new OrderItemRequest(1L, "스마트폰", 2, 500000L),
                        new OrderItemRequest(3L, "노트북", 1, 1000000L)));

        Order order = createOrder();
        when(orderUseCase.createOrder(any(CreateOrderCommand.class))).thenReturn(order);

        // When & Then
        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.totalAmount").value(2000000L))
                .andExpect(jsonPath("$.data.status").value("PAID"))
                .andExpect(jsonPath("$.data.createdAt").exists());
    }

    @Test
    @DisplayName("주문 및 결제 API 테스트 - 재고 부족")
    void shouldReturnConflictWhenInsufficientStock() throws Exception {
        // Given
        CreateOrderCommand request = new CreateOrderCommand(
                1L,
                List.of(new OrderItemRequest(1L, "스마트폰", 2, 500000L)));

        when(orderUseCase.createOrder(any(CreateOrderCommand.class)))
                .thenThrow(new InsufficientStockException("재고가 부족합니다"));

        // When & Then
        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("INSUFFICIENT_STOCK"));
    }

    @Test
    @DisplayName("주문 및 결제 API 테스트 - 잔액 부족")
    void shouldReturnConflictWhenInsufficientBalance() throws Exception {
        // Given
        CreateOrderCommand request = new CreateOrderCommand(
                1L,
                List.of(new OrderItemRequest(1L, "스마트폰", 2, 500000L)));

        when(orderUseCase.createOrder(any(CreateOrderCommand.class)))
                .thenThrow(new InsufficientBalanceException("잔액이 부족합니다"));

        // When & Then
        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("INSUFFICIENT_BALANCE"));
    }

    private Order createOrder() {
        List<OrderItem> items = List.of(
                new OrderItem(1L, "스마트폰", 2, 500000L),
                new OrderItem(3L, "노트북", 1, 1000000L));
        Order order = new Order("ORD20250101000001", 1L, items);
        order.setId(1L);
        order.completePayment(); // COMPLETED 상태로 변경
        return order;
    }
}
