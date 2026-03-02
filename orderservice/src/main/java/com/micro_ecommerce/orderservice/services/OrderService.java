package com.micro_ecommerce.orderservice.services;

import java.util.List;

import com.micro_ecommerce.orderservice.dto.OrderRequestDto;
import com.micro_ecommerce.orderservice.mapper.OrderMapper;
import com.micro_ecommerce.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderRequestDto)
                .toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .map(orderMapper::toOrderRequestDto)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

}
