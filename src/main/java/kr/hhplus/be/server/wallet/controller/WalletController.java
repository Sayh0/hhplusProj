package kr.hhplus.be.server.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.wallet.service.WalletService;
import kr.hhplus.be.server.wallet.vo.WalletVo;
import kr.hhplus.be.server.wallet.vo.WalletChargeRequestVo;
import kr.hhplus.be.server.wallet.vo.WalletChargeResponseVo;

/**
 * 고객 잔고 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * 고객 잔고 조회
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<WalletVo>> findWalletByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(ApiResponse.success(walletService.findWalletByCustomerId(customerId)));
    }

    /**
     * 포인트 충전
     */
    @PostMapping("/charge")
    public ResponseEntity<ApiResponse<WalletChargeResponseVo>> chargePoint(@RequestBody WalletChargeRequestVo request) {
        return ResponseEntity.ok(ApiResponse.success(walletService.chargePoint(request)));
    }
}
