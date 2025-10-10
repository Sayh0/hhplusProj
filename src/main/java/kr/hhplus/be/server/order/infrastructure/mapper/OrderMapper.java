package kr.hhplus.be.server.order.infrastructure.mapper;

import kr.hhplus.be.server.order.infrastructure.dto.OrderVo;
import kr.hhplus.be.server.order.infrastructure.dto.OrderItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Order 관련 MyBatis Mapper
 */
@Mapper
public interface OrderMapper {

    /**
     * 주문을 저장합니다.
     */
    void insertOrder(OrderVo order);

    /**
     * 주문 항목을 저장합니다.
     */
    void insertOrderItem(OrderItemVo orderItem);

    /**
     * 주문을 업데이트합니다.
     */
    void updateOrder(OrderVo order);

    /**
     * 주문번호로 주문을 조회합니다.
     */
    OrderVo selectOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 주문 ID로 주문을 조회합니다.
     */
    OrderVo selectOrderById(@Param("id") Long id);

    /**
     * 고객 ID로 주문 목록을 조회합니다.
     */
    List<OrderVo> selectOrdersByCustomerId(@Param("customerId") Long customerId);

    /**
     * 주문 ID로 주문 항목 목록을 조회합니다.
     */
    List<OrderItemVo> selectOrderItemsByOrderId(@Param("orderId") Long orderId);
}
