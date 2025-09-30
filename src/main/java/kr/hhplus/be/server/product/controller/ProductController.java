package kr.hhplus.be.server.product.controller;

import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.product.service.ProductService;
import kr.hhplus.be.server.product.vo.ProductVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductVo>>> getAllProducts() {
        // RED 단계: 빈 리스트를 반환
        // return ResponseEntity.ok(ApiResponse.success(List.of()));
        List<ProductVo> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success(products));
    }
}
