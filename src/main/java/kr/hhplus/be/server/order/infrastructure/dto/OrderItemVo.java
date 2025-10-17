package kr.hhplus.be.server.order.infrastructure.dto;

import lombok.Getter;

/**
 * 데이터베이스 OrderItem 테이블과 매핑되는 VO
 */
@Getter
public class OrderItemVo {

    private final Long id;
    private final Long orderId;
    private final Long productId;
    private final String name;
    private final Integer quantity;
    private final Long unitPrice;
    private final Long totalPrice;

    public OrderItemVo(Long id, Long orderId, Long productId, String name,
            Integer quantity, Long unitPrice, Long totalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
