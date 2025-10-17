package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.domain.entity.Coupon;

import java.math.BigDecimal;

public final class CouponDiscountCalculator {

    private CouponDiscountCalculator() {
    }

    public static long calculateDiscount(Coupon coupon, long orderAmount) {
        if (orderAmount <= 0) {
            return 0L;
        }

        BigDecimal order = BigDecimal.valueOf(orderAmount);
        BigDecimal min = coupon.getMinOrderAmount() == null ? BigDecimal.ZERO : coupon.getMinOrderAmount();
        if (order.compareTo(min) < 0) {
            return 0L;
        }

        if ("FIXED_AMOUNT".equals(coupon.getDiscountType())) {
            long discount = coupon.getDiscountValue().longValue();
            return Math.min(discount, orderAmount);
        }

        if ("PERCENTAGE".equals(coupon.getDiscountType())) {
            BigDecimal percent = coupon.getDiscountValue();
            BigDecimal raw = order.multiply(percent).divide(BigDecimal.valueOf(100));
            long calculated = raw.longValue();
            long max = coupon.getMaxDiscountAmount() == null ? 0L : coupon.getMaxDiscountAmount().longValue();
            if (max > 0) {
                calculated = Math.min(calculated, max);
            }
            return Math.min(calculated, orderAmount);
        }

        return 0L;
    }
}
