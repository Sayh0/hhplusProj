package kr.hhplus.be.server.coupon.infrastructure.mapper;

import kr.hhplus.be.server.coupon.infrastructure.vo.CouponVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Coupon MyBatis Mapper 인터페이스
 */
@Mapper
public interface CouponMapper {
    
    /**
     * 쿠폰 ID로 쿠폰 조회
     */
    CouponVo selectCouponById(Long couponId);
}

