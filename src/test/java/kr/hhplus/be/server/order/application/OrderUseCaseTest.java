package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.CreateOrderCommand;
import kr.hhplus.be.server.order.application.dto.OrderItemRequest;
import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderItem;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.domain.vo.OrderStatus;
import kr.hhplus.be.server.coupon.domain.entity.Coupon;
import kr.hhplus.be.server.coupon.domain.entity.CustomerCoupon;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.domain.repository.CustomerCouponRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CustomerCouponRepository customerCouponRepository;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    @DisplayName("고객ID가 null이면 예외 발생")
    void shouldThrowExceptionWhenCustomerIdIsNull() {
        // When & Then
        assertThatThrownBy(() -> new CreateOrderCommand(
                null,
                List.of(new OrderItemRequest(1L, "스마트폰", 1, 500000L))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객ID는 필수입니다.");
    }

    @Test
    @DisplayName("주문 항목이 비어있으면 예외 발생")
    void shouldThrowExceptionWhenItemsIsEmpty() {
        // When & Then
        assertThatThrownBy(() -> new CreateOrderCommand(1L, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목은 1개 이상이어야 합니다.");
    }

    @Test
    @DisplayName("주문 생성 성공")
    void shouldCreateOrderSuccess() {
        // Given
        CreateOrderCommand command = new CreateOrderCommand(
                1L,
                List.of(
                        new OrderItemRequest(1L, "스마트폰", 2, 500000L),
                        new OrderItemRequest(2L, "케이스", 1, 30000L)));

        // When
        Order order = orderUseCase.createOrder(command);

        // Then
        assertThat(order.getCustomerId()).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getItems()).hasSize(2);
        assertThat(order.getTotalAmount()).isEqualTo(1030000L);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("존재하지 않는 주문의 결제 완료 시 예외 발생")
    void shouldThrowExceptionWhenOrderNotFound() {
        // Given
        String orderNo = "ORD20250101000001";
        when(orderRepository.findByOrderNo(orderNo)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> orderUseCase.completePayment(orderNo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문을 찾을 수 없습니다: " + orderNo);
    }

    @Test
    @DisplayName("결제 성공")
    void shouldPaymentSuccess() {
        // Given
        String orderNo = "ORD20250101000001";
        Order order = createOrder();
        when(orderRepository.findByOrderNo(orderNo)).thenReturn(order);

        // When
        Order result = orderUseCase.completePayment(orderNo);

        // Then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PAID);
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("주문 생성 유스케이스를 실행 성공")
    void shouldExecuteCreateOrderUseCaseSuccess() {
        // Given
        CreateOrderCommand command = new CreateOrderCommand(
                1L,
                List.of(new OrderItemRequest(1L, "스마트폰", 1, 500000L)));

        // When
        Order order = orderUseCase.createOrder(command);

        // Then
        assertThat(order.getCustomerId()).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("결제 완료 유스케이스를 실행 성공")
    void shouldExecuteCompletePaymentUseCaseSuccess() {
        // Given
        String orderNo = "ORD20250101000001";
        Order order = createOrder();
        when(orderRepository.findByOrderNo(orderNo)).thenReturn(order);

        // When
        Order result = orderUseCase.completePayment(orderNo);

        // Then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PAID);
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("고객 ID로 주문 목록 조회 성공")
    void shouldFindOrdersByCustomerIdSuccess() {
        // Given
        Long customerId = 1L;
        List<Order> orders = List.of(createOrder());
        when(orderRepository.findByCustomerId(customerId)).thenReturn(orders);

        // When
        List<Order> result = orderUseCase.findOrdersByCustomerId(customerId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo(customerId);
    }

    // ===== 쿠폰 기능 테스트 =====

    @Test
    @DisplayName("쿠폰을 사용하여 주문 생성 성공 테스트")
    void shouldCreateOrderWhenUsingValidCoupon() {
        // given
        Long customerId = 1L;
        Long customerCouponNo = 100L;
        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(1L, "상품1", 2, 10000L),
                new OrderItemRequest(2L, "상품2", 1, 5000L));
        CreateOrderCommand command = new CreateOrderCommand(customerId, items, customerCouponNo);

        // 쿠폰 정보 설정
        Coupon coupon = createFixedAmountCoupon(5000L);
        CustomerCoupon customerCoupon = createCustomerCoupon(customerCouponNo, customerId, 1L);

        when(customerCouponRepository.findById(customerCouponNo)).thenReturn(Optional.of(customerCoupon));
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Order order = orderUseCase.createOrder(command);

        // then
        assertThat(order.getCustomerId()).isEqualTo(customerId);
        assertThat(order.getOriginalAmount()).isEqualTo(25000L);
        assertThat(order.getDiscountAmount()).isEqualTo(5000L);
        assertThat(order.getTotalAmount()).isEqualTo(20000L);
        assertThat(order.getCustomerCouponNo()).isEqualTo(customerCouponNo);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰을 사용할 때 주문 생성하면 예외 발생")
    void shouldThrowExceptionWhenUsingNonExistentCoupon() {
        // given
        Long customerId = 1L;
        Long customerCouponNo = 999L;
        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(1L, "상품1", 1, 10000L));
        CreateOrderCommand command = new CreateOrderCommand(customerId, items, customerCouponNo);

        when(customerCouponRepository.findById(customerCouponNo)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> orderUseCase.createOrder(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰을 찾을 수 없습니다: " + customerCouponNo);
    }

    @Test
    @DisplayName("사용할 수 없는 쿠폰을 사용할 때 주문 생성하면 예외 발생")
    void shouldThrowExceptionWhenUsingUnavailableCoupon() {
        // given
        Long customerId = 1L;
        Long customerCouponNo = 100L;
        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(1L, "상품1", 1, 10000L));
        CreateOrderCommand command = new CreateOrderCommand(customerId, items, customerCouponNo);

        // 이미 사용된 쿠폰
        CustomerCoupon usedCoupon = createUsedCustomerCoupon(customerCouponNo, customerId, 1L);
        when(customerCouponRepository.findById(customerCouponNo)).thenReturn(Optional.of(usedCoupon));

        // when & then
        assertThatThrownBy(() -> orderUseCase.createOrder(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사용할 수 없는 쿠폰입니다.");
    }

    @Test
    @DisplayName("만료된 쿠폰을 사용할 때 주문 생성하면 예외 발생")
    void shouldThrowExceptionWhenUsingExpiredCoupon() {
        // given
        Long customerId = 1L;
        Long customerCouponNo = 100L;
        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(1L, "상품1", 1, 10000L));
        CreateOrderCommand command = new CreateOrderCommand(customerId, items, customerCouponNo);

        // 만료된 쿠폰
        CustomerCoupon expiredCoupon = createExpiredCustomerCoupon(customerCouponNo, customerId, 1L);
        when(customerCouponRepository.findById(customerCouponNo)).thenReturn(Optional.of(expiredCoupon));

        // when & then
        assertThatThrownBy(() -> orderUseCase.createOrder(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("만료된 쿠폰입니다.");
    }

    @Test
    @DisplayName("최소 주문 금액 미만일 때 쿠폰을 사용하면 예외 발생")
    void shouldThrowExceptionWhenOrderAmountIsLessThanMinimum() {
        // given
        Long customerId = 1L;
        Long customerCouponNo = 100L;
        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(1L, "상품1", 1, 10000L) // 10000원 주문
        );
        CreateOrderCommand command = new CreateOrderCommand(customerId, items, customerCouponNo);

        // 최소 주문 금액 30000원인 쿠폰
        Coupon coupon = createFixedAmountCouponWithMinimum(5000L, 30000L);
        CustomerCoupon customerCoupon = createCustomerCoupon(customerCouponNo, customerId, 1L);

        when(customerCouponRepository.findById(customerCouponNo)).thenReturn(Optional.of(customerCoupon));
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

        // when & then
        assertThatThrownBy(() -> orderUseCase.createOrder(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액을 만족하지 않습니다. 필요: 30000원, 현재: 10000원");
    }

    // ===== 헬퍼 메소드 =====

    // 모의 주문 생성용 헬퍼
    private Order createOrder() {
        List<OrderItem> items = List.of(new OrderItem(1L, "스마트폰", 1, 500000L));
        return new Order("ORD20250101000001", 1L, items);
    }

    private Coupon createFixedAmountCoupon(long discountValue) {
        return new Coupon(
                1L, "고정금액쿠폰", "DISCOUNT", "FIXED_AMOUNT",
                BigDecimal.valueOf(discountValue), BigDecimal.ZERO, BigDecimal.ZERO,
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Coupon createFixedAmountCouponWithMinimum(long discountValue, long minOrderAmount) {
        return new Coupon(
                1L, "최소주문금액쿠폰", "DISCOUNT", "FIXED_AMOUNT",
                BigDecimal.valueOf(discountValue), BigDecimal.valueOf(minOrderAmount), BigDecimal.ZERO,
                LocalDateTime.now(), LocalDateTime.now());
    }

    private CustomerCoupon createCustomerCoupon(Long customerCouponNo, Long customerId, Long couponId) {
        return new CustomerCoupon(
                customerCouponNo, customerId, couponId, "AVAILABLE",
                LocalDateTime.now(), LocalDateTime.now().plusDays(30), null,
                LocalDateTime.now(), LocalDateTime.now());
    }

    private CustomerCoupon createUsedCustomerCoupon(Long customerCouponNo, Long customerId, Long couponId) {
        return new CustomerCoupon(
                customerCouponNo, customerId, couponId, "USED",
                LocalDateTime.now(), LocalDateTime.now().plusDays(30), LocalDateTime.now(),
                LocalDateTime.now(), LocalDateTime.now());
    }

    private CustomerCoupon createExpiredCustomerCoupon(Long customerCouponNo, Long customerId, Long couponId) {
        return new CustomerCoupon(
                customerCouponNo, customerId, couponId, "AVAILABLE",
                LocalDateTime.now(), LocalDateTime.now().minusDays(1), null,
                LocalDateTime.now(), LocalDateTime.now());
    }

}