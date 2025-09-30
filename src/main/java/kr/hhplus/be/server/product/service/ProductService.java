package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.product.mapper.ProductMapper;
import kr.hhplus.be.server.product.vo.ProductVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<ProductVo> getAllProducts() {
        // // TODO: TDD 단계별로 구현 예정
        // return null;
        // TDD Green 단계: 테스트를 통과시키기 위한 최소한의 구현
        // return new ArrayList<>();
        // Mapper에 위임하여 실제 데이터를 조회한다
        return productMapper.findAllProducts();
    }
}