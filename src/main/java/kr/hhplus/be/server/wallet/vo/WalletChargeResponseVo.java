package kr.hhplus.be.server.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 충전 응답 VO
 */
@Getter
@AllArgsConstructor
public final class WalletChargeResponseVo {
    private final Long customerId;
    private final Long chargeAmount;
    private final Long balanceAfter;
    private final String chargeDate;
}
