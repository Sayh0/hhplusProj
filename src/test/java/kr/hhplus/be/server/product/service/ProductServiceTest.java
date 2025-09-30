package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.product.vo.ProductVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        // 각 테스트 전에 실행되는 초기화 코드
        // 현재는 ProductService에 의존성이 없어서 비어있음
    }

    @Test
    @DisplayName("상품 목록 조회 - 성공 케이스")
    void getAllProducts_Success() {
        // Given (준비) - 테스트에 필요한 데이터와 상황을 설정
        // When (실행) - 테스트하고자 하는 메서드를 호출
        List<ProductVo> result = productService.getAllProducts();
        
        // Then (검증) - 결과가 예상과 일치하는지 확인
        assertThat(result).isNotNull();
    }
}
