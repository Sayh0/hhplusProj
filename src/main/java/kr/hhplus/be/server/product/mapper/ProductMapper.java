package kr.hhplus.be.server.product.mapper;

import kr.hhplus.be.server.product.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductVo> findAllProducts();
}


