package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.CreateOrderCommand;
import kr.hhplus.be.server.order.application.dto.OrderItemRequest;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderItem;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.vo.OrderStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    @DisplayName("고객ID가 null이면 예외 발생")
    void shouldThrowExceptionWhenCustomerIdIsNull() {
        // When & Then
        assertThatThrownBy(() -> new CreateOrderCommand(
                null,
                List.of(new OrderItemRequest(1L, "스마트폰", 1, 500000L))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객ID는 필수입니다.");
    }

    @Test
    @DisplayName("주문 항목이 비어있으면 예외 발생")
    void shouldThrowExceptionWhenItemsIsEmpty() {
        // When & Then
        assertThatThrownBy(() -> new CreateOrderCommand(1L, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목은 1개 이상이어야 합니다.");
    }

    @Test
    @DisplayName("주문 생성 성공")
    void shouldCreateOrderSuccess() {
        // Given
        CreateOrderCommand command = new CreateOrderCommand(
                1L,
                List.of(
                        new OrderItemRequest(1L, "스마트폰", 2, 500000L),
                        new OrderItemRequest(2L, "케이스", 1, 30000L)));

        // When
        Order order = orderUseCase.createOrder(command);

        // Then
        assertThat(order.getCustomerId()).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getItems()).hasSize(2);
        assertThat(order.getTotalAmount()).isEqualTo(1030000L);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("존재하지 않는 주문의 결제 완료 시 예외 발생")
    void shouldThrowExceptionWhenOrderNotFound() {
        // Given
        String orderNo = "ORD20250101000001";
        when(orderRepository.findByOrderNo(orderNo)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> orderUseCase.completePayment(orderNo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문을 찾을 수 없습니다: " + orderNo);
    }

    @Test
    @DisplayName("결제 성공")
    void shouldPaymentSuccess() {
        // Given
        String orderNo = "ORD20250101000001";
        Order order = createOrder();
        when(orderRepository.findByOrderNo(orderNo)).thenReturn(order);

        // When
        Order result = orderUseCase.completePayment(orderNo);

        // Then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PAID);
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("주문 생성 유스케이스를 실행 성공")
    void shouldExecuteCreateOrderUseCaseSuccess() {
        // Given
        CreateOrderCommand command = new CreateOrderCommand(
                1L,
                List.of(new OrderItemRequest(1L, "스마트폰", 1, 500000L)));

        // When
        Order order = orderUseCase.createOrder(command);

        // Then
        assertThat(order.getCustomerId()).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("결제 완료 유스케이스를 실행 성공")
    void shouldExecuteCompletePaymentUseCaseSuccess() {
        // Given
        String orderNo = "ORD20250101000001";
        Order order = createOrder();
        when(orderRepository.findByOrderNo(orderNo)).thenReturn(order);

        // When
        Order result = orderUseCase.completePayment(orderNo);

        // Then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PAID);
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("고객 ID로 주문 목록 조회 성공")
    void shouldFindOrdersByCustomerIdSuccess() {
        // Given
        Long customerId = 1L;
        List<Order> orders = List.of(createOrder());
        when(orderRepository.findByCustomerId(customerId)).thenReturn(orders);

        // When
        List<Order> result = orderUseCase.findOrdersByCustomerId(customerId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo(customerId);
    }

    // 모의 주문 생성용 헬퍼
    private Order createOrder() {
        List<OrderItem> items = List.of(new OrderItem(1L, "스마트폰", 1, 500000L));
        return new Order("ORD20250101000001", 1L, items);
    }

}