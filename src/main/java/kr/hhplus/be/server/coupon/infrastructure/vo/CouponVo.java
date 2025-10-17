package kr.hhplus.be.server.coupon.infrastructure.vo;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 데이터베이스 COUPON 테이블과 매핑되는 VO
 */
@Getter
public class CouponVo {
    
    private final Long couponId;
    private final String couponName;
    private final String couponType; // CODE 참조 (e.g., DISCOUNT)
    private final String discountType; // FIXED_AMOUNT, PERCENTAGE
    private final BigDecimal discountValue; // 고정금액이면 금액, 비율이면 퍼센트
    private final BigDecimal minOrderAmount; // 최소 주문 금액
    private final BigDecimal maxDiscountAmount; // 비율 할인 시 상한선
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CouponVo(Long couponId, String couponName, String couponType, String discountType,
                    BigDecimal discountValue, BigDecimal minOrderAmount, BigDecimal maxDiscountAmount,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponType = couponType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

