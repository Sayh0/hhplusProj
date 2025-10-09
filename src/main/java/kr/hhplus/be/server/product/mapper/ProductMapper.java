package kr.hhplus.be.server.product.mapper;

import kr.hhplus.be.server.product.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 상품 데이터 접근 매퍼
 */
@Mapper
public interface ProductMapper {
    /**
     * 전체 상품 목록 조회
     */
    List<ProductVo> findAllProducts();
}
