package com.micro_ecommerce.orderservice.clients;

import com.micro_ecommerce.orderservice.dto.OrderRequestDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventoryservice", path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping("/products/updateStock")
    public double updateProductStock(@RequestBody OrderRequestDto orderRequest);

}
