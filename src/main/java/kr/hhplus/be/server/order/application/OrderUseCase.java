package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.CreateOrderCommand;
import kr.hhplus.be.server.order.application.dto.OrderItemRequest;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderItem;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 주문 관련 비즈니스 로직을 담당하는 유스케이스
 */
@Component
@RequiredArgsConstructor
@Transactional
public class OrderUseCase {

    private final OrderRepository orderRepository;

    /**
     * 주문을 생성합니다.
     *
     * @param command 주문 생성 명령
     * @return 생성된 주문
     */
    public Order createOrder(CreateOrderCommand command) {
        // OrderItem 생성
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : command.getItems()) {
            OrderItem orderItem = new OrderItem(
                    itemRequest.getProductId(),
                    itemRequest.getName(),
                    itemRequest.getQuantity(),
                    itemRequest.getUnitPrice());
            orderItems.add(orderItem);
        }

        // Order 생성
        String orderNo = generateOrderNo();
        Order order = new Order(orderNo, command.getCustomerId(), orderItems);

        // 저장
        orderRepository.save(order);

        return order;
    }

    /**
     * 주문 결제를 완료합니다.
     *
     * @param orderNo 주문번호
     * @return 결제 완료된 주문
     */
    public Order completePayment(String orderNo) {
        Order order = findOrder(orderNo);
        order.completePayment();
        orderRepository.save(order);
        return order;
    }

    /**
     * 주문번호로 주문을 조회합니다.
     *
     * @param orderNo 주문번호
     * @return 조회된 주문
     * @throws IllegalArgumentException 주문을 찾을 수 없는 경우
     */
    @Transactional(readOnly = true)
    public Order findOrder(String orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null) {
            throw new IllegalArgumentException("주문을 찾을 수 없습니다: " + orderNo);
        }
        return order;
    }

    /**
     * 고객의 주문 목록을 조회합니다.
     *
     * @param customerId 고객 ID
     * @return 주문 목록
     */
    @Transactional(readOnly = true)
    public List<Order> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    /**
     * 주문번호를 생성합니다.
     * <p>
     * 생성 형식: 현재시간(밀리초 13자리) + 난수(4자리)
     * </p>
     * <p>
     * 예시: 17031234567891234
     * </p>
     */
    private String generateOrderNo() {
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 10000); // 0~9999
        return String.format("%d%04d", timestamp, random);
    }
}