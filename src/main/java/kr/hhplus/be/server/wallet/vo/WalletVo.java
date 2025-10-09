package kr.hhplus.be.server.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 잔고 정보 VO
 */
@Getter
@AllArgsConstructor
public final class WalletVo {
    private final Long customerId;
    private final Long balance;
    private final String firstInputDttm;
    private final String firstInputId;
    private final String lastInputDttm;
    private final String lastInputId;
}
