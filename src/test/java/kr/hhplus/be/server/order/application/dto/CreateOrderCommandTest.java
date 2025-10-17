package kr.hhplus.be.server.order.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateOrderCommandTest {

    @Test
    @DisplayName("고객ID가 null이면 예외 발생")
    void shouldThrowExceptionWhenCustomerIdIsNull() {
        // When & Then
        assertThatThrownBy(() -> new CreateOrderCommand(null, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객ID는 필수입니다.");
    }

    @Test
    @DisplayName("주문 항목이 null이면 예외 발생")
    void shouldThrowExceptionWhenItemsIsNull() {
        // When & Then
        assertThatThrownBy(() -> new CreateOrderCommand(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목은 1개 이상이어야 합니다.");
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
    @DisplayName("CreateOrderCommand 생성 성공")
    void shouldCreateCommandWhenValidDataGiven() {
        // Given
        Long customerId = 1L;
        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(1L, "스마트폰", 2, 500000L),
                new OrderItemRequest(2L, "케이스", 1, 30000L));

        // When
        CreateOrderCommand command = new CreateOrderCommand(customerId, items);

        // Then
        assertThat(command.getCustomerId()).isEqualTo(customerId);
        assertThat(command.getItems()).isEqualTo(items);
    }
}