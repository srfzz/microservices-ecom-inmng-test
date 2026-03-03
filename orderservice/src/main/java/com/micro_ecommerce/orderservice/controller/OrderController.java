package com.micro_ecommerce.orderservice.controller;

import java.util.List;

import com.micro_ecommerce.orderservice.dto.OrderRequestDto;
import com.micro_ecommerce.orderservice.services.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/helloOrderService")
    public String helloOrderService() {
        return "Hello from Order Service!";
    }
    

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders() {

        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
