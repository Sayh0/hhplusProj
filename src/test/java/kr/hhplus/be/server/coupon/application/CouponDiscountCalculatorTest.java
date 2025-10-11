package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 쿠폰 할인 계산기 테스트
 */
class CouponDiscountCalculatorTest {

    @Test
    @DisplayName("고정 금액 할인 쿠폰으로 할인 금액 계산 성공 테스트")
    void shouldReturnFixedAmountWhenUsingFixedAmountCoupon() {
        // given
        Coupon coupon = createFixedAmountCoupon(5000L);
        long orderAmount = 25000L;

        // when
        long discountAmount = CouponDiscountCalculator.calculateDiscount(coupon, orderAmount);

        // then
        assertThat(discountAmount).isEqualTo(5000L);
    }

    @Test
    @DisplayName("비율 할인 쿠폰으로 할인 금액 계산 성공 테스트")
    void shouldReturnCalculatedAmountWhenUsingPercentageCoupon() {
        // given
        Coupon coupon = createPercentageCoupon(20L, 10000L); // 20% 할인, 최대 10000원
        long orderAmount = 50000L;

        // when
        long discountAmount = CouponDiscountCalculator.calculateDiscount(coupon, orderAmount);

        // then
        assertThat(discountAmount).isEqualTo(10000L); // 50000 * 0.2 = 10000, 최대 할인 금액과 동일
    }

    @Test
    @DisplayName("비율 할인 시 최대 할인 금액 제한 성공 테스트")
    void shouldReturnMaxDiscountAmountWhenCalculatedAmountExceedsMax() {
        // given
        Coupon coupon = createPercentageCoupon(50L, 5000L); // 50% 할인, 최대 5000원
        long orderAmount = 20000L;

        // when
        long discountAmount = CouponDiscountCalculator.calculateDiscount(coupon, orderAmount);

        // then
        assertThat(discountAmount).isEqualTo(5000L); // 20000 * 0.5 = 10000이지만 최대 5000원
    }

    @Test
    @DisplayName("최소 주문 금액 미만일 때 할인 금액 0원 반환 성공 테스트")
    void shouldReturnZeroWhenOrderAmountIsLessThanMinimum() {
        // given
        Coupon coupon = createFixedAmountCouponWithMinimum(5000L, 30000L); // 5000원 할인, 최소 30000원
        long orderAmount = 25000L;

        // when
        long discountAmount = CouponDiscountCalculator.calculateDiscount(coupon, orderAmount);

        // then
        assertThat(discountAmount).isEqualTo(0L);
    }

    @Test
    @DisplayName("할인 금액이 주문 금액을 초과할 때 주문 금액만큼 할인 성공 테스트")
    void shouldReturnOrderAmountWhenDiscountExceedsOrderAmount() {
        // given
        Coupon coupon = createFixedAmountCoupon(30000L); // 30000원 할인
        long orderAmount = 10000L;

        // when
        long discountAmount = CouponDiscountCalculator.calculateDiscount(coupon, orderAmount);

        // then
        assertThat(discountAmount).isEqualTo(10000L); // 주문 금액을 초과하지 않음
    }

    private Coupon createFixedAmountCoupon(long discountValue) {
        return new Coupon(
                1L, "고정금액쿠폰", "DISCOUNT", "FIXED_AMOUNT",
                BigDecimal.valueOf(discountValue), BigDecimal.ZERO, BigDecimal.ZERO,
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Coupon createPercentageCoupon(long discountValue, long maxDiscountAmount) {
        return new Coupon(
                2L, "비율할인쿠폰", "DISCOUNT", "PERCENTAGE",
                BigDecimal.valueOf(discountValue), BigDecimal.ZERO, BigDecimal.valueOf(maxDiscountAmount),
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Coupon createFixedAmountCouponWithMinimum(long discountValue, long minOrderAmount) {
        return new Coupon(
                3L, "최소주문금액쿠폰", "DISCOUNT", "FIXED_AMOUNT",
                BigDecimal.valueOf(discountValue), BigDecimal.valueOf(minOrderAmount), BigDecimal.ZERO,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
