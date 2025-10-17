package kr.hhplus.be.server.product.controller;

import kr.hhplus.be.server.product.service.ProductService;
import kr.hhplus.be.server.product.vo.ProductVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ProductController 테스트
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    /**
     * 상품 목록 조회 성공 테스트
     */
    @Test
    @DisplayName("상품 목록 조회 API - 성공 케이스")
    void getAllProducts_Success() throws Exception {
        // Given: 서비스가 반환할 데이터 준비
        List<ProductVo> mockProducts = List.of(
            new ProductVo(
              1L,                    // productId
              "마우스",              // productName
              15000L,                // productPrice
              10,                    // productStock
              "ON_SALE",             // productStatus
              "게이밍 마우스",        // productDescription
              "2024-01-01 10:00:00", // firstInputDttm
              "SYSTEM",              // firstInputId
              "2024-01-01 10:00:00", // lastInputDttm
              "SYSTEM"               // lastInputId
            ),
            new ProductVo(
              2L,                    // productId
              "키보드",              // productName
              80000L,                // productPrice
              5,                     // productStock
              "ON_SALE",             // productStatus
              "기계식 키보드",        // productDescription
              "2024-01-01 10:00:00", // firstInputDttm
              "SYSTEM",              // firstInputId
              "2024-01-01 10:00:00", // lastInputDttm
              "SYSTEM"               // lastInputId
            )
        );
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // When & Then: HTTP 요청/응답 검증
        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].productName").value("마우스"))
                .andExpect(jsonPath("$.data[0].productPrice").value(15000))
                .andExpect(jsonPath("$.data[1].productName").value("키보드"))
                .andExpect(jsonPath("$.data[1].productPrice").value(80000));
    }

    /**
     * 상품 목록 조회 빈 목록 테스트
     */
    @Test
    @DisplayName("상품 목록 조회 API - 빈 목록 케이스")
    void getAllProducts_EmptyList() throws Exception {
        // Given: 서비스가 빈 목록 반환
        when(productService.getAllProducts()).thenReturn(List.of());

        // When & Then: HTTP 요청/응답 검증
        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }
}
