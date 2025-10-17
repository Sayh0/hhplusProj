package kr.hhplus.be.server.wallet.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.customer.service.CustomerService;
import kr.hhplus.be.server.wallet.mapper.WalletMapper;
import kr.hhplus.be.server.wallet.vo.WalletVo;
import kr.hhplus.be.server.wallet.vo.WalletChargeHistoryVo;
import kr.hhplus.be.server.wallet.vo.WalletChargeRequestVo;
import kr.hhplus.be.server.wallet.vo.WalletChargeResponseVo;

/**
 * 고객 잔고 관련 비즈니스 로직 서비스
 */
@Service
public class WalletService {

    private final WalletMapper walletMapper;
    private final CustomerService customerService;

    public WalletService(WalletMapper walletMapper, CustomerService customerService) {
        this.walletMapper = walletMapper;
        this.customerService = customerService;
    }

    /**
     * 고객ID로 잔고 조회
     */
    public WalletVo findWalletByCustomerId(Long customerId) {
        // 1. 입력 검증
        if (customerId == null) {
            throw new IllegalArgumentException("고객 ID는 필수입니다");
        }

        // 2. 고객 존재 여부 확인
        if (!customerService.existsCustomer(customerId)) {
            throw new IllegalArgumentException("존재하지 않는 고객입니다");
        }
        return walletMapper.findWalletByCustomerId(customerId);
    }

    /**
     * 포인트 충전
     */
    public WalletChargeResponseVo chargePoint(WalletChargeRequestVo request) {
        // 1. 입력 검증
        if (request.getPaymentMethod() == null || request.getPaymentMethod().trim().isEmpty()) {
            throw new IllegalArgumentException("결제 수단은 필수입니다");
        }

        if (request.getChargeAmount() == null || request.getChargeAmount() <= 0) {
            throw new IllegalArgumentException("충전 금액은 0보다 커야 합니다");
        }

        // 2. 고객 존재 여부 확인
        if (!customerService.existsCustomer(request.getCustomerId())) {
            throw new IllegalArgumentException("존재하지 않는 고객입니다");
        }

        // 3. 잔고 상태에 따른 분기 처리
        WalletVo existingWallet = walletMapper.findWalletByCustomerId(request.getCustomerId());
        Long newBalance = request.getChargeAmount();

        if (existingWallet == null) {
            // 새 잔고 생성
            walletMapper.insertWallet(request.getCustomerId(), newBalance, "SYSTEM");
        } else {
            // 기존 잔고 업데이트
            Long updatedBalance = existingWallet.getBalance() + request.getChargeAmount();
            walletMapper.updateWalletBalance(request.getCustomerId(), updatedBalance, "SYSTEM");
            newBalance = updatedBalance;
        }

        // 4. 충전 히스토리 저장
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        WalletChargeHistoryVo history = new WalletChargeHistoryVo(
                null, // chargeId는 DB에서 자동 생성
                request.getCustomerId(),
                request.getChargeAmount(),
                request.getPaymentMethod(),
                "SUCCESS",
                newBalance,
                currentTime,
                currentTime,
                "SYSTEM",
                currentTime,
                "SYSTEM");

        walletMapper.insertWalletChargeHistory(history);

        // 5. 응답 생성
        WalletChargeResponseVo response = new WalletChargeResponseVo(
                request.getCustomerId(),
                request.getChargeAmount(),
                newBalance,
                currentTime);

        return response;

    }
}
