package com.micro_ecommerce.orderservice.repository;

import com.micro_ecommerce.orderservice.entity.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
