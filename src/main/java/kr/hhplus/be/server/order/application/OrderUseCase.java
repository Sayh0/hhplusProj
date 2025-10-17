package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.CreateOrderCommand;
import kr.hhplus.be.server.order.application.dto.OrderItemRequest;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderItem;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.coupon.application.CouponDiscountCalculator;
import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.entity.CustomerCoupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.domain.repository.CustomerCouponRepository;
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
    private final CouponRepository couponRepository;
    private final CustomerCouponRepository customerCouponRepository;

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

        // 원래 금액 계산
        long originalAmount = 0L;
        for (OrderItem item : orderItems) {
            originalAmount += item.getTotalPrice();
        }

        // 주문 생성
        String orderNo = generateOrderNo();
        Order order;
        Long customerCouponNo = command.getCustomerCouponNo();

        if (customerCouponNo != null) {
            // 쿠폰 사용 주문
            long discountAmount = validateAndCalculateDiscount(customerCouponNo, originalAmount);
            order = new Order(orderNo, command.getCustomerId(), orderItems, customerCouponNo, discountAmount);
        } else {
            // 쿠폰 미사용 주문
            order = new Order(orderNo, command.getCustomerId(), orderItems);
        }

        // 저장
        orderRepository.save(order);

        return order;
    }

    /**
     * 쿠폰 검증 및 할인 금액 계산
     *
     * @param customerCouponNo 고객 쿠폰 번호
     * @param originalAmount   원래 주문 금액
     * @return 할인 금액
     * @throws IllegalArgumentException 쿠폰을 찾을 수 없거나 최소 주문 금액 미만인 경우
     * @throws IllegalStateException    만료되었거나 사용할 수 없는 쿠폰인 경우
     */
    private long validateAndCalculateDiscount(Long customerCouponNo, long originalAmount) {
        // 고객 쿠폰 조회
        CustomerCoupon customerCoupon = customerCouponRepository.findById(customerCouponNo)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다: " + customerCouponNo));

        // 만료 여부 체크 (구체적 메시지)
        if (customerCoupon.getExpireDate() != null
                && java.time.LocalDateTime.now().isAfter(customerCoupon.getExpireDate())) {
            throw new IllegalStateException("만료된 쿠폰입니다.");
        }

        // 사용 가능 상태 체크
        if (!"AVAILABLE".equals(customerCoupon.getStatus())) {
            throw new IllegalStateException("사용할 수 없는 쿠폰입니다.");
        }

        // 쿠폰 마스터 조회
        Coupon coupon = couponRepository.findById(customerCoupon.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다: " + customerCouponNo));

        // 최소 주문 금액 검증
        long minOrder = coupon.getMinOrderAmount() == null ? 0L : coupon.getMinOrderAmount().longValue();
        if (originalAmount < minOrder) {
            throw new IllegalArgumentException(
                    "최소 주문 금액을 만족하지 않습니다. 필요: " + minOrder + "원, 현재: " + originalAmount + "원");
        }

        // 할인 금액 계산
        return CouponDiscountCalculator.calculateDiscount(coupon, originalAmount);
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