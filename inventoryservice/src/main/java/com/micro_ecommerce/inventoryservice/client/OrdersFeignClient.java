package com.micro_ecommerce.inventoryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "orderservice", path = "/orders")
public interface OrdersFeignClient {
    @GetMapping("/core/helloOrderService")
    String helloOrderService();

}
