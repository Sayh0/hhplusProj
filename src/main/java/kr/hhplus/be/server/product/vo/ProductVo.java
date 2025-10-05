package kr.hhplus.be.server.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo {
    private Long productId;
    private String productName;
    private Long productPrice;
    private Integer productStock;
    private String productStatus;
    private String productDescription;
}