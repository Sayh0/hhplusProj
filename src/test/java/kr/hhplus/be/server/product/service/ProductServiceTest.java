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

// Mockito 프레임워크를 사용하기 위한 설정
// Mockito는 가짜 객체(Mock)를 만들어주는 도구
// 나중에 Repository/Mapper 같은 의존성을 가짜로 만들어서 테스트할 때 사용
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    // 서비스가 의존하는 Mapper를 목으로 준비
    @Mock
    private ProductMapper productMapper;

    // @InjectMocks: 테스트하고자 하는 실제 객체를 생성, 위의 @Mock 들을 주입
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        // 각 테스트 전에 실행되는 초기화 코드
    }
    // Given-When-Then 패턴
    @Test
    @DisplayName("상품 목록 조회 - 성공")
    void getAllProducts_ReturnsListFromMapper() {
        // Given: Mapper가 반환할 가짜 데이터 세팅
        List<ProductVo> mocked = List.of(
                new ProductVo(
                    1L
                    , "마우스"
                    , 15000L
                    , 10
                    , "ON_SALE"
                    , "게이밍 마우스"
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
