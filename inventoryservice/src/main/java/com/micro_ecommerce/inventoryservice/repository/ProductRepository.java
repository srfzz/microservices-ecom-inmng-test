package com.micro_ecommerce.inventoryservice.repository;

import com.micro_ecommerce.inventoryservice.entity.ProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
