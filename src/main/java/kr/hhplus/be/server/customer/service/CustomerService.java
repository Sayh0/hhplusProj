package kr.hhplus.be.server.customer.service;

import org.springframework.stereotype.Service;

/**
 * 고객 관련 비즈니스 로직 서비스
 */
@Service
public class CustomerService {

    /**
     * 고객 존재 여부 확인
     */
    public boolean existsCustomer(Long customerId) {
        if (customerId == null) {
            return false;
        }
        
        // TODO: 실제 고객 존재 여부 확인 로직 구현
        // 임시로 customerId가 1L 이상이면 존재하는 고객으로 처리
        return customerId >= 1L;
    }
}
