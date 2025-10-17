package kr.hhplus.be.server.coupon.domain.repository;

import kr.hhplus.be.server.coupon.domain.entity.CustomerCoupon;

import java.util.Optional;

public interface CustomerCouponRepository {
    Optional<CustomerCoupon> findById(Long customerCouponNo);
}
