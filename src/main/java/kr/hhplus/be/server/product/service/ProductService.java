package kr.hhplus.be.server.product.service;

import kr.hhplus.be.server.product.mapper.ProductMapper;
import kr.hhplus.be.server.product.vo.ProductVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 상품 관련 비즈니스 로직 서비스
 */
@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * 전체 상품 목록 조회
     */
    public List<ProductVo> getAllProducts() {
        return productMapper.findAllProducts();
    }
}