package kr.hhplus.be.server.order.infrastructure.dto;

import kr.hhplus.be.server.order.domain.vo.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 데이터베이스 Order 테이블과 매핑되는 VO
 */
@Getter
public class OrderVo {
    
    private final Long id;
    private final String orderNo;
    private final Long customerId;
    private final OrderStatus status;
    private final Long originalAmount; // 할인 전 금액
    private final Long discountAmount; // 할인 금액
    private final Long customerCouponNo; // 사용된 쿠폰 번호 (nullable)
    private final Long totalAmount; // 최종 결제 금액
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    
    // OrderItem 리스트 (1:N 관계)
    private final List<OrderItemVo> items;
    
    public OrderVo(Long id, String orderNo, Long customerId, OrderStatus status,
                   Long originalAmount, Long discountAmount, Long customerCouponNo,
                   Long totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt,
                   List<OrderItemVo> items) {
        this.id = id;
        this.orderNo = orderNo;
        this.customerId = customerId;
        this.status = status;
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.customerCouponNo = customerCouponNo;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
    }
}
