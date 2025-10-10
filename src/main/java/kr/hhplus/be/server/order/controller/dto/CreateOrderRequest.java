package kr.hhplus.be.server.order.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 주문 및 결제 요청 DTO (API 명세서 6.1에 맞춤)
 */
public class CreateOrderRequest {

    @NotNull(message = "사용자 ID는 필수입니다")
    @JsonProperty("userId")
    private Long userId;

    @NotEmpty(message = "주문 항목은 1개 이상이어야 합니다")
    @Size(min = 1, message = "주문 항목은 1개 이상이어야 합니다")
    @JsonProperty("items")
    private List<OrderItemDto> items;

    @JsonProperty("couponId")
    private Long couponId; // 선택사항

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Long userId, List<OrderItemDto> items, Long couponId) {
        this.userId = userId;
        this.items = items;
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}
