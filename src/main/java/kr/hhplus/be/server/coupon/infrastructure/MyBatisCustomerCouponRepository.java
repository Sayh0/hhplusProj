package kr.hhplus.be.server.coupon.infrastructure;

import kr.hhplus.be.server.coupon.domain.entity.CustomerCoupon;
import kr.hhplus.be.server.coupon.domain.repository.CustomerCouponRepository;
import kr.hhplus.be.server.coupon.infrastructure.mapper.CustomerCouponMapper;
import kr.hhplus.be.server.coupon.infrastructure.vo.CustomerCouponVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MyBatis 기반 CustomerCouponRepository 구현체
 */
@Repository
@RequiredArgsConstructor
public class MyBatisCustomerCouponRepository implements CustomerCouponRepository {

    private final CustomerCouponMapper customerCouponMapper;

    @Override
    public Optional<CustomerCoupon> findById(Long customerCouponNo) {
        CustomerCouponVo vo = customerCouponMapper.selectCustomerCouponById(customerCouponNo);
        return vo != null ? Optional.of(toCustomerCoupon(vo)) : Optional.empty();
    }

    /**
     * CustomerCouponVo를 CustomerCoupon 도메인 엔티티로 변환
     */
    private CustomerCoupon toCustomerCoupon(CustomerCouponVo vo) {
        return new CustomerCoupon(
                vo.getCustomerCouponNo(),
                vo.getCustomerId(),
                vo.getCouponId(),
                vo.getStatus(),
                vo.getIssueDate(),
                vo.getExpireDate(),
                vo.getUseDate(),
                vo.getCreatedAt(),
                vo.getUpdatedAt()
        );
    }
}

