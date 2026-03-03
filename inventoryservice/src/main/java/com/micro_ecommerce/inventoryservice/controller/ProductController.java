package com.micro_ecommerce.inventoryservice.controller;

import java.util.List;

import com.micro_ecommerce.inventoryservice.client.OrdersFeignClient;
import com.micro_ecommerce.inventoryservice.dto.OrderRequestDto;
import com.micro_ecommerce.inventoryservice.dto.ProductReponseDto;
import com.micro_ecommerce.inventoryservice.services.ProductService;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    public ProductController(ProductService productService, DiscoveryClient discoveryClient, RestClient restClient,
            OrdersFeignClient ordersFeignClient) {
        this.productService = productService;
        this.discoveryClient = discoveryClient;
        this.restClient = restClient;
        this.ordersFeignClient = ordersFeignClient;
    }

    @GetMapping("/helloInventoryService")
    public String helloInventoryService() {

        return ordersFeignClient.helloOrderService();

        // ServiceInstance instance = discoveryClient.getInstances("orderservice")
        // .stream().findFirst()
        // .orElseThrow(() -> new RuntimeException("No instances of inventory-service
        // found"));
        // String response = restClient.get()
        // .uri(instance.getUri() + "/orders/core/helloOrderService")
        // .retrieve()
        // .body(String.class);

        // return "Hello from Inventory Service!" + response + " from " +
        // instance.getUri();
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

    @PutMapping("/updateStock")
    public ResponseEntity<Double> updateProductStock(@RequestBody OrderRequestDto orderRequest) {
        log.info("Received request to update stock for products: {}", orderRequest);
        Double totalPrice = productService.updateProductStock(orderRequest);
        return ResponseEntity.ok(totalPrice);
    }

}
