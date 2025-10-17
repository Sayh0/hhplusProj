package kr.hhplus.be.server.order.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderCommand {

    private final Long customerId;
    private final List<OrderItemRequest> items;
    private final Long customerCouponNo; // nullable

    /*
     * // 생성자
     * public CreateOrderCommand(Long customerId, List<OrderItemRequest> items) {
     * if (customerId == null) {
     * throw new IllegalArgumentException("고객ID는 필수입니다.");
     * }
     * if (items == null || items.isEmpty()) {
     * throw new IllegalArgumentException("주문 항목은 1개 이상이어야 합니다.");
     * }
     * this.customerId = customerId;
     * this.items = items;
     * this.customerCouponNo = null;
     * }
     */
    // 생성자 (쿠폰 없음)
    public CreateOrderCommand(Long customerId, List<OrderItemRequest> items) {
        this(customerId, items, null);
    }

    /*
     * // 쿠폰사용 엔티티 생성자
     * public CreateOrderCommand(Long customerId, List<OrderItemRequest> items, Long
     * customerCouponNo) {
     * if (customerId == null) {
     * throw new IllegalArgumentException("고객ID는 필수입니다.");
     * }
     * if (items == null || items.isEmpty()) {
     * throw new IllegalArgumentException("주문 항목은 1개 이상이어야 합니다.");
     * }
     * if (customerCouponNo != null && customerCouponNo <= 0) {
     * throw new IllegalArgumentException("쿠폰 번호는 null이거나 양수여야 합니다.");
     * }
     * this.customerId = customerId;
     * this.items = items;
     * this.customerCouponNo = customerCouponNo;
     * }
     */

    /**
     * Jackson이 여러 생성자 중 어떤 것을 사용할지 알 수 없어 에러 발생...
     * JSON 역직렬화용 생성자 (Jackson이 사용)
     * - @JsonCreator: final 필드를 가진 불변 객체에서 생성자가 여러 개일 때 Jackson이 어떤 것을 사용할지 명시
     * - @JsonProperty: JSON 필드명과 생성자 파라미터를 매핑 (REST API @RequestBody 역직렬화 시 필요)
     */
    @JsonCreator
    public CreateOrderCommand(
            @JsonProperty("customerId") Long customerId,
            @JsonProperty("items") List<OrderItemRequest> items,
            @JsonProperty("customerCouponNo") Long customerCouponNo) {
        if (customerId == null) {
            throw new IllegalArgumentException("고객ID는 필수입니다.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 1개 이상이어야 합니다.");
        }
        if (customerCouponNo != null && customerCouponNo <= 0) {
            throw new IllegalArgumentException("쿠폰 번호는 null이거나 양수여야 합니다.");
        }

        this.customerId = customerId;
        this.items = items;
        this.customerCouponNo = customerCouponNo;
    }

}