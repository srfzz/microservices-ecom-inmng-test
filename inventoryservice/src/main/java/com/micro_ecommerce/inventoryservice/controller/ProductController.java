package com.micro_ecommerce.inventoryservice.controller;

import java.util.List;

import com.micro_ecommerce.inventoryservice.dto.ProductReponseDto;
import com.micro_ecommerce.inventoryservice.services.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductReponseDto>> listAllProducts() {
        log.info("Received request to list all products");
        List<ProductReponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReponseDto> getProductById(@PathVariable Long id) {
        log.info("Received request to get product with id: {}", id);
        ProductReponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

}
