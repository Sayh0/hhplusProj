package kr.hhplus.be.server.wallet.service;

import kr.hhplus.be.server.customer.service.CustomerService;
import kr.hhplus.be.server.wallet.mapper.WalletMapper;
import kr.hhplus.be.server.wallet.vo.WalletChargeRequestVo;
import kr.hhplus.be.server.wallet.vo.WalletChargeResponseVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

/**
 * WalletService 테스트
 */
@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletMapper walletMapper;
    
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        // 각 테스트 전에 실행되는 초기화 코드
    }

    /*
     * ## 3. 테스트 우선순위
        ### 🔥 필수 (1순위)
        1. **Null 체크** - NullPointerException 방지
        2. **빈 값 체크** - 빈 문자열로 인한 비즈니스 로직 오류 방지
        3. **음수/0 체크** - 비즈니스 규칙 위반 방지
        ### ⚡ 권장 (2순위)
        4. **범위 초과 체크** - 시스템 안정성, 보안
        5. **존재하지 않는 리소스** - 데이터 일관성, 외래키 제약
        + 추가 : insert/ update 유효성 테스트
     *
     */

     /**
      * 고객ID null 일 시 잔고 조회 실패 테스트
     */
    @Test
    @DisplayName("고객ID null 일 시 잔고 조회 실패")
    void shouldThrowExceptionWhenCustomerIdIsNull() {
        // Given: 테스트 데이터 준비
        Long customerId = null;

        // When: 테스트 실행
        assertThatThrownBy(() -> walletService.findWalletByCustomerId(customerId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("고객 ID는 필수입니다");
    }
    // 고객 Id가 null 이거나 빈 값 혹은 공백인 경우는?
    // ㄴcustomerId는 Long 타입이므로 빈 값이나 공백은 없음.
    
    /**
     * 결제수단이 빈 문자열일 시 포인트 충전 실패 테스트
     */
    @Test
    @DisplayName("결제수단이 빈 문자열이면 포인트 충전 실패")
    void shouldThrowExceptionWhenPaymentMethodIsEmpty() {
        // Given: 테스트 데이터 준비
        WalletChargeRequestVo request = new WalletChargeRequestVo(1L, 10000L, "");

        // When: 테스트 실행
        assertThatThrownBy(() -> walletService.chargePoint(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("결제 수단은 필수입니다");
    }

    /**
     * 충전 금액이 음수일 시 포인트 충전 실패 테스트
     */
    @Test
    @DisplayName("충전 금액이 음수면 포인트 충전 실패")
    void shouldThrowExceptionWhenChargeAmountIsNegative() {
        // Given: 테스트 데이터 준비
        WalletChargeRequestVo request = new WalletChargeRequestVo(1L, -1000L, "CARD");

        // When: 테스트 실행
        assertThatThrownBy(() -> walletService.chargePoint(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("충전 금액은 0보다 커야 합니다");
    }

    /**
     * 충전 금액이 0일 시 포인트 충전 실패 테스트
     */
    @Test
    @DisplayName("충전 금액이 0이면 포인트 충전 실패")
    void shouldThrowExceptionWhenChargeAmountIsZero() {
        // Given: 테스트 데이터 준비
        WalletChargeRequestVo request = new WalletChargeRequestVo(1L, 0L, "CARD");

        // When: 테스트 실행
        assertThatThrownBy(() -> walletService.chargePoint(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("충전 금액은 0보다 커야 합니다");
    }

    /**
     * 존재하지 않는 고객으로 포인트 충전 실패 테스트
     */
    @Test
    @DisplayName("존재하지 않는 고객이면 포인트 충전 실패")
    void shouldThrowExceptionWhenCustomerDoesNotExist() {
        // Given: 테스트 데이터 준비
        WalletChargeRequestVo request = new WalletChargeRequestVo(999L, 10000L, "CARD");
        
        // Mock 설정: 존재하지 않는 고객
        when(customerService.existsCustomer(999L)).thenReturn(false);

        // When: 테스트 실행
        assertThatThrownBy(() -> walletService.chargePoint(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("존재하지 않는 고객입니다");
    }

    /**
     * 기존 잔고가 없으면 새로 생성하는 테스트
     */
    @Test
    @DisplayName("기존 잔고가 없으면 새로 생성")
    void shouldCreateNewWalletWhenNoExistingWallet() {
        // Given: 기존 잔고가 없는 상황
        WalletChargeRequestVo request = new WalletChargeRequestVo(1L, 10000L, "CARD");
        // 추가 : 실제 유효한 사용자인지 검증
        when(customerService.existsCustomer(1L)).thenReturn(true);
        when(walletMapper.findWalletByCustomerId(1L)).thenReturn(null);

        // When: 포인트 충전
        WalletChargeResponseVo result = walletService.chargePoint(request);

        // Then: 새 잔고 생성 확인
        verify(walletMapper).insertWallet(any(), any(), any());
        verify(walletMapper, never()).updateWalletBalance(any(), any(), any());
    }
}
