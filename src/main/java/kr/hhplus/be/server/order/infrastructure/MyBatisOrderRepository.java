package kr.hhplus.be.server.order.infrastructure;

import kr.hhplus.be.server.order.domain.entity.Order;
import kr.hhplus.be.server.order.domain.entity.OrderItem;
import kr.hhplus.be.server.order.domain.repository.OrderRepository;
import kr.hhplus.be.server.order.infrastructure.dto.OrderVo;
import kr.hhplus.be.server.order.infrastructure.dto.OrderItemVo;
import kr.hhplus.be.server.order.infrastructure.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MyBatis 기반 OrderRepository 구현체
 */
@Repository
@RequiredArgsConstructor
public class MyBatisOrderRepository implements OrderRepository {

    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public Order save(Order order) {
        OrderVo orderVo = toOrderVo(order);

        if (orderVo.getId() == null) {
            // 새 주문 생성
            orderVo = new OrderVo(
                    null, // ID는 자동 생성
                    orderVo.getOrderNo(),
                    orderVo.getCustomerId(),
                    orderVo.getStatus(),
                    orderVo.getOriginalAmount(),
                    orderVo.getDiscountAmount(),
                    orderVo.getCustomerCouponNo(),
                    orderVo.getTotalAmount(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    null // items는 별도로 저장
            );
            orderMapper.insertOrder(orderVo);

            // OrderItem들도 저장
            for (OrderItem item : order.getItems()) {
                OrderItemVo itemVo = toOrderItemVo(item, orderVo.getId());
                orderMapper.insertOrderItem(itemVo);
            }
        } else {
            // 기존 주문 업데이트
            orderVo = new OrderVo(
                    orderVo.getId(),
                    orderVo.getOrderNo(),
                    orderVo.getCustomerId(),
                    orderVo.getStatus(),
                    orderVo.getOriginalAmount(),
                    orderVo.getDiscountAmount(),
                    orderVo.getCustomerCouponNo(),
                    orderVo.getTotalAmount(),
                    orderVo.getCreatedAt(),
                    LocalDateTime.now(),
                    orderVo.getItems());
            orderMapper.updateOrder(orderVo);
        }

        return toOrder(orderVo);
    }

    @Override
    public Order findByOrderNo(String orderNo) {
        OrderVo orderVo = orderMapper.selectOrderByOrderNo(orderNo);
        return orderVo != null ? toOrder(orderVo) : null;
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        List<OrderVo> orderVos = orderMapper.selectOrdersByCustomerId(customerId);
        return orderVos.stream()
                .map(this::toOrder)
                .collect(Collectors.toList());
    }

    /**
     * Order 도메인 엔티티를 OrderVo로 변환
     */
    private OrderVo toOrderVo(Order order) {
        return new OrderVo(
                order.getId(),
                order.getOrderNo(),
                order.getCustomerId(),
                order.getStatus(),
                order.getOriginalAmount(),
                order.getDiscountAmount(),
                order.getCustomerCouponNo(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                null, // updatedAt
                null // items는 별도 처리
        );
    }

    /**
     * OrderItem 도메인 엔티티를 OrderItemVo로 변환
     */
    private OrderItemVo toOrderItemVo(OrderItem item, Long orderId) {
        return new OrderItemVo(
                null, // ID는 자동 생성
                orderId,
                item.getProductId(),
                item.getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice());
    }

    /**
     * OrderVo를 Order 도메인 엔티티로 변환
     */
    private Order toOrder(OrderVo vo) {
        List<OrderItem> items = vo.getItems() != null
                ? vo.getItems().stream()
                        .map(this::toOrderItem)
                        .collect(Collectors.toList())
                : List.of();

        Order order;
        if (vo.getCustomerCouponNo() != null) {
            // 쿠폰 사용 주문
            order = new Order(vo.getOrderNo(), vo.getCustomerId(), items,
                    vo.getCustomerCouponNo(), vo.getDiscountAmount());
        } else {
            // 쿠폰 미사용 주문
            order = new Order(vo.getOrderNo(), vo.getCustomerId(), items);
        }
        order.setId(vo.getId());
        return order;
    }

    /**
     * OrderItemVo를 OrderItem 도메인 엔티티로 변환
     */
    private OrderItem toOrderItem(OrderItemVo vo) {
        return new OrderItem(
                vo.getProductId(),
                vo.getName(),
                vo.getQuantity(),
                vo.getUnitPrice());
    }
}
