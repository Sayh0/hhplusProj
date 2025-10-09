package kr.hhplus.be.server.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ProductVo {
    private final Long productId;
    private final String productName;
    private final Long productPrice;
    private final Integer productStock;
    private final String productStatus;
    private final String productDescription;
}