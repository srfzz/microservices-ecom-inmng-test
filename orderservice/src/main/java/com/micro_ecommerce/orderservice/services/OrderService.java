package com.micro_ecommerce.orderservice.services;

import java.math.BigDecimal;
import java.util.List;

import com.micro_ecommerce.orderservice.clients.InventoryFeignClient;
import com.micro_ecommerce.orderservice.dto.OrderRequestDto;
import com.micro_ecommerce.orderservice.entity.OrderEntity;
import com.micro_ecommerce.orderservice.entity.OrderStatus;
import com.micro_ecommerce.orderservice.mapper.OrderMapper;
import com.micro_ecommerce.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryFeignClient inventoryFeignClient;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper,
            InventoryFeignClient inventoryFeignClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.inventoryFeignClient = inventoryFeignClient;
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

    @Transactional
    public OrderRequestDto createOrder(OrderRequestDto orderRequest) {
        log.info("Creating order with request: {}", orderRequest);
        double updatedStock = inventoryFeignClient.updateProductStock(orderRequest);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'createOrder'");
        OrderEntity order = orderMapper.toOrderEntity(orderRequest);
        for (var item : order.getItems()) {
            item.setOrder(order);
        }
        order.setTotalPrice(BigDecimal.valueOf(updatedStock));
        order.setStatus(OrderStatus.CONFIRMED);
        OrderEntity savedOrder = orderRepository.save(order);
        return orderMapper.toOrderRequestDto(savedOrder);

    }

}
