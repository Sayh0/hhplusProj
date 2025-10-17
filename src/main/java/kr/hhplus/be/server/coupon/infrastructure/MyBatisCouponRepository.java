package kr.hhplus.be.server.coupon.infrastructure;

import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.infrastructure.mapper.CouponMapper;
import kr.hhplus.be.server.coupon.infrastructure.vo.CouponVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MyBatis 기반 CouponRepository 구현체
 */
@Repository
@RequiredArgsConstructor
public class MyBatisCouponRepository implements CouponRepository {

    private final CouponMapper couponMapper;

    @Override
    public Optional<Coupon> findById(Long couponId) {
        CouponVo couponVo = couponMapper.selectCouponById(couponId);
        return couponVo != null ? Optional.of(toCoupon(couponVo)) : Optional.empty();
    }

    /**
     * CouponVo를 Coupon 도메인 엔티티로 변환
     */
    private Coupon toCoupon(CouponVo vo) {
        return new Coupon(
                vo.getCouponId(),
                vo.getCouponName(),
                vo.getCouponType(),
                vo.getDiscountType(),
                vo.getDiscountValue(),
                vo.getMinOrderAmount(),
                vo.getMaxDiscountAmount(),
                vo.getCreatedAt(),
                vo.getUpdatedAt()
        );
    }
}

