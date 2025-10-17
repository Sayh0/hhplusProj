package kr.hhplus.be.server.wallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.hhplus.be.server.wallet.service.WalletService;
import kr.hhplus.be.server.wallet.vo.WalletChargeRequestVo;
import kr.hhplus.be.server.wallet.vo.WalletChargeResponseVo;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @Mock // 모의 객체
    private WalletService walletService;

    @InjectMocks // 실제 테스트할 객체
    private WalletController walletController;

    private MockMvc mockMvc; // 웹 요청 테스트용 모의 객체 [가상 웹 브라우저]

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController)
                .build();
    }

    /**
     * 유효한 요청이 제공될 시 포인트 충전 성공 테스트
     * 
     * @throws Exception
     */
    @Test
    @DisplayName("유효한 요청이 제공될 시 포인트 충전 성공 테스트")
    void shouldChargePointSuccessWhenValidRequestProvided() throws Exception {
        // Given
        String requestBody = """
                {
                    "customerId": 1,
                    "chargeAmount": 10000,
                    "paymentMethod": "CARD"
                }
                """;

        WalletChargeResponseVo expectedResponse = new WalletChargeResponseVo(
                1L, 10000L, 10000L, "2024-01-01 10:00:00");

        // When
        when(walletService.chargePoint(any(WalletChargeRequestVo.class)))
                .thenReturn(expectedResponse);

        // Then
        mockMvc.perform(
                post("/api/wallets/charge")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.customerId").value(1))
                .andExpect(jsonPath("$.data.chargeAmount").value(10000))
                .andExpect(jsonPath("$.data.balanceAfter").value(10000))
                .andExpect(jsonPath("$.data.chargeDate").value("2024-01-01 10:00:00"));
    }
}
