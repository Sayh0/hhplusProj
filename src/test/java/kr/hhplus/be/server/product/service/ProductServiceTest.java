package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.product.mapper.ProductMapper;
import kr.hhplus.be.server.product.vo.ProductVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * ProductService 테스트
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        // 각 테스트 전에 실행되는 초기화 코드
    }

    /**
     * 상품 목록 조회 성공 테스트
     */
    @Test
    @DisplayName("상품 목록 조회 - 성공")
    void getAllProducts_ReturnsListFromMapper() {
        // Given: Mapper가 반환할 가짜 데이터 세팅
        List<ProductVo> mocked = List.of(
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
                )
        );
        // When-1: Mapper가 반환할 가짜 데이터를 정의
        when(productMapper.findAllProducts()).thenReturn(mocked);
        // When-2: 서비스 호출
        List<ProductVo> result = new ArrayList<ProductVo>();
        result = productService.getAllProducts();

        // Then: Mapper 결과를 그대로 돌려주는지 검증
        // assertThat(result).isNotNull();
        // assertThat(result).hasSize(1);
        // assertThat(result.get(0).getProductName()).isEqualTo("마우스");
        
        // AssertJ 체이닝을 활용한 개선된 검증
        assertThat(result)
            .hasSize(1)
            .first() // first()는 null 안전하게 첫 번째 요소를 가져옴...
            .satisfies(product -> {
                assertThat(product.getProductName()).isEqualTo("마우스");
                assertThat(product.getProductPrice()).isEqualTo(15000L);
                assertThat(product.getProductStock()).isEqualTo(10);
                assertThat(product.getProductStatus()).isEqualTo("ON_SALE");
                assertThat(product.getProductDescription()).isEqualTo("게이밍 마우스");
            });
    }

    /**
     * 상품 목록 조회 빈 목록 테스트
     */
    @Test
    @DisplayName("상품 목록 조회 - 빈 목록이면 그대로 반환")
    void getAllProducts_ReturnsEmptyList() {
        // Given
        when(productMapper.findAllProducts()).thenReturn(List.of());

        // When
        List<ProductVo> result = productService.getAllProducts();

        // Then
        assertThat(result).isEmpty();
    }
}
