package kr.hhplus.be.server.wallet.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * 포인트 충전 요청 VO
 */
@Getter
public final class WalletChargeRequestVo {
    private final Long customerId;
    private final Long chargeAmount;
    private final String paymentMethod;

    @JsonCreator
    public WalletChargeRequestVo(
            @JsonProperty("customerId") Long customerId,
            @JsonProperty("chargeAmount") Long chargeAmount,
            @JsonProperty("paymentMethod") String paymentMethod) {
        this.customerId = customerId;
        this.chargeAmount = chargeAmount;
        this.paymentMethod = paymentMethod;
    }
}
