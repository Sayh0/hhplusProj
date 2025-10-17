package kr.hhplus.be.server.coupon.domain.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Coupon {

    private final Long id;
    private final String name;
    private final String type; // CODE 참조용 일반 타입 (확장 대비)
    private final String discountType; // FIXED_AMOUNT | PERCENTAGE
    private final BigDecimal discountValue; // 금액 또는 퍼센트 값
    private final BigDecimal minOrderAmount; // 최소 주문 금액 (nullable 허용 시 0으로 표현)
    private final BigDecimal maxDiscountAmount; // 퍼센트 시 상한 (없으면 0)
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Coupon(Long id,
            String name,
            String type,
            String discountType,
            BigDecimal discountValue,
            BigDecimal minOrderAmount,
            BigDecimal maxDiscountAmount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
