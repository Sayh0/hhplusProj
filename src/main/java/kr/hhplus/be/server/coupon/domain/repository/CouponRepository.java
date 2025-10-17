package kr.hhplus.be.server.coupon.domain.repository;

import kr.hhplus.be.server.coupon.domain.entity.Coupon;

import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> findById(Long couponId);
    // 기타 필요한 메서드 추가 예정
}
