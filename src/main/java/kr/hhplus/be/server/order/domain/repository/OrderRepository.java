package kr.hhplus.be.server.order.domain.repository;

import kr.hhplus.be.server.order.domain.entity.Order;

/**
 * 주문 저장소 인터페이스
 * 도메인 레이어에서 정의하여 인프라스트럭처 레이어에서 구현
 */
public interface OrderRepository {

    /**
     * 주문을 저장합니다.
     *
     * @param order 저장할 주문
     * @return 저장된 주문
     */
    Order save(Order order);

    /**
     * 주문번호로 주문을 조회합니다.
     *
     * @param orderNo 주문번호
     * @return 조회된 주문, 없으면 null
     */
    Order findByOrderNo(String orderNo);

    /**
     * 고객 ID로 주문 목록을 조회합니다.
     *
     * @param customerId 고객 ID
     * @return 주문 목록
     */
    java.util.List<Order> findByCustomerId(Long customerId);
}
