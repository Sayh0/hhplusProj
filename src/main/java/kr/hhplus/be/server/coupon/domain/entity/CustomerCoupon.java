package kr.hhplus.be.server.coupon.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerCoupon {

    private final Long customerCouponNo;
    private final Long customerId;
    private final Long couponId;
    private final String status; // AVAILABLE | USED (CODE 참조)
    private final LocalDateTime issueDate;
    private final LocalDateTime expireDate;
    private final LocalDateTime useDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CustomerCoupon(Long customerCouponNo,
            Long customerId,
            Long couponId,
            String status,
            LocalDateTime issueDate,
            LocalDateTime expireDate,
            LocalDateTime useDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
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

    public boolean isAvailable() {
        if (!"AVAILABLE".equals(this.status)) {
            return false;
        }
        if (this.expireDate != null && LocalDateTime.now().isAfter(this.expireDate)) {
            return false;
        }
        return true;
    }
}
