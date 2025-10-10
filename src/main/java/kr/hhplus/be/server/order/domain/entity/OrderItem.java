package kr.hhplus.be.server.order.domain.entity;

import lombok.Getter;

/**
 * 주문 항목
 */
@Getter
public class OrderItem {

    private final Long productId;
    private final String name;
    private final int quantity;
    private final long unitPrice;

    // 생성자
    public OrderItem(Long productId, String name, int quantity, long unitPrice) {
        if (productId == null) {
            throw new IllegalArgumentException("productId는 필수입니다.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name은 필수입니다.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity는 1 이상이어야 합니다.");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("unitPrice는 1 이상이어야 합니다.");
        }

        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * 주문 항목의 총 가격을 계산합니다.
     * 단가(unitPrice)와 수량(quantity)을 곱한 값을 반환합니다.
     *
     * @return 단가 × 수량의 총 가격
     */
    public long getTotalPrice() {
        return unitPrice * quantity;
    }
}
