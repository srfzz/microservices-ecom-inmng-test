package com.micro_ecommerce.inventoryservice.services;

import java.util.List;
import java.util.stream.Collectors;

import com.micro_ecommerce.inventoryservice.dto.OrderRequestDto;
import com.micro_ecommerce.inventoryservice.dto.ProductReponseDto;
import com.micro_ecommerce.inventoryservice.mapper.ProductMapper;
import com.micro_ecommerce.inventoryservice.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional()
    public Double updateProductStock(OrderRequestDto orderRequest) {

        log.info("Updating stock for products in order request: {}", orderRequest);
        double totalPrice = 0.0;

        for (var item : orderRequest.getItems()) {
            var productOpt = productRepository.findById(item.getProductId());
            if (productOpt.isEmpty()) {
                throw new RuntimeException("Product not found with id: " + item.getProductId());
            }
            var product = productOpt.get();
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product id: " + item.getProductId());
            }
            product.setStock(product.getStock() - item.getQuantity());
            totalPrice += product.getPrice() * item.getQuantity();
            productRepository.save(product);
        }

        return totalPrice;
    }
}
