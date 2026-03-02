package com.micro_ecommerce.orderservice.dto;

import java.math.BigDecimal;
import java.util.List;

import com.micro_ecommerce.orderservice.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<OrderItemRequestDto> items;

}
