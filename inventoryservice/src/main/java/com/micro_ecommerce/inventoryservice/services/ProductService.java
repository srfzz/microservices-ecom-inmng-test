package com.micro_ecommerce.inventoryservice.services;

import java.util.List;
import java.util.stream.Collectors;

import com.micro_ecommerce.inventoryservice.dto.ProductReponseDto;
import com.micro_ecommerce.inventoryservice.mapper.ProductMapper;
import com.micro_ecommerce.inventoryservice.repository.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductReponseDto> getAllProducts() {
        log.info("Fetching all products from the inventory");
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    public ProductReponseDto getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .map(productMapper::toProductResponseDto)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
