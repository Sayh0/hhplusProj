package kr.hhplus.be.server.coupon.infrastructure.mapper;

import kr.hhplus.be.server.coupon.infrastructure.vo.CustomerCouponVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * CustomerCoupon MyBatis Mapper 인터페이스
 */
@Mapper
public interface CustomerCouponMapper {
    
    /**
     * 고객 쿠폰 번호로 고객 쿠폰 조회
     */
    CustomerCouponVo selectCustomerCouponById(Long customerCouponNo);
    
    /**
     * 고객 쿠폰 상태 업데이트 (사용 처리)
     */
    void updateCustomerCouponStatus(CustomerCouponVo customerCouponVo);
}

