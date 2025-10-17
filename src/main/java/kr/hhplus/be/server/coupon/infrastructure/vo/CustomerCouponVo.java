package kr.hhplus.be.server.coupon.infrastructure.vo;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 데이터베이스 CUSTOMER_COUPON 테이블과 매핑되는 VO
 */
@Getter
public class CustomerCouponVo {
    
    private final Long customerCouponNo;
    private final Long customerId;
    private final Long couponId;
    private final String status; // AVAILABLE | USED (CODE 참조)
    private final LocalDateTime issueDate;
    private final LocalDateTime expireDate;
    private final LocalDateTime useDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CustomerCouponVo(Long customerCouponNo, Long customerId, Long couponId,
                           String status, LocalDateTime issueDate, LocalDateTime expireDate,
                           LocalDateTime useDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerCouponNo = customerCouponNo;
        this.customerId = customerId;
        this.couponId = couponId;
        this.status = status;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.useDate = useDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

