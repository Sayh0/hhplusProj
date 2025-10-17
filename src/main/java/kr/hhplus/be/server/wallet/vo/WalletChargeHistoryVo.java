package kr.hhplus.be.server.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 충전 내역 VO
 */
@Getter
@AllArgsConstructor
public final class WalletChargeHistoryVo {
    private final Long chargeId;
    private final Long customerId;
    private final Long chargeAmount;
    private final String paymentMethod;
    private final String paymentStatus;
    private final Long balanceAfter;
    private final String chargeDate;
    private final String firstInputDttm;
    private final String firstInputId;
    private final String lastInputDttm;
    private final String lastInputId;
}
