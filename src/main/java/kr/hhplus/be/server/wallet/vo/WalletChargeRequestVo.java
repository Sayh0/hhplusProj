package kr.hhplus.be.server.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 충전 요청 VO
 */
@Getter
@AllArgsConstructor
public final class WalletChargeRequestVo {
    private final Long customerId;
    private final Long chargeAmount;
    private final String paymentMethod;
}
