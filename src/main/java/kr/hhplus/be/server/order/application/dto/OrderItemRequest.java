package kr.hhplus.be.server.order.application.dto;

import lombok.Getter;

@Getter
public class OrderItemRequest {

    private final Long productId;
    private final String name;
    private final int quantity;
    private final long unitPrice;

    // 생성자
    public OrderItemRequest(Long productId, String name, int quantity, long unitPrice) {
        if (productId == null) {
            throw new IllegalArgumentException("상품ID는 필수입니다.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("단가는 1 이상이어야 합니다.");
        }

        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}