package com.micro_ecommerce.orderservice.mapper;

import com.micro_ecommerce.orderservice.dto.OrderItemRequestDto;
import com.micro_ecommerce.orderservice.dto.OrderRequestDto;
import com.micro_ecommerce.orderservice.entity.OrderEntity;
import com.micro_ecommerce.orderservice.entity.OrderItemEntity;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderRequestDto toOrderRequestDto(OrderEntity orderEntity);

    OrderEntity toOrderEntity(OrderRequestDto orderRequestDto);

    OrderItemEntity toOrderItemEntity(OrderItemRequestDto dto);

    OrderEntity updateOrderEntityFromRequestDto(OrderRequestDto orderRequestDto,
            @MappingTarget OrderEntity orderEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderEntity patchOrderEntityFromRequestDto(OrderRequestDto orderRequestDto,
            @MappingTarget OrderEntity orderEntity);

}
