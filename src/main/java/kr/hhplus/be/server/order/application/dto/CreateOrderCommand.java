package kr.hhplus.be.server.order.application.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderCommand {

    private final Long customerId;
    private final List<OrderItemRequest> items;

    // 생성자
    public CreateOrderCommand(Long customerId, List<OrderItemRequest> items) {
        if (customerId == null) {
            throw new IllegalArgumentException("고객ID는 필수입니다.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 1개 이상이어야 합니다.");
        }

        this.customerId = customerId;
        this.items = items;
    }
}