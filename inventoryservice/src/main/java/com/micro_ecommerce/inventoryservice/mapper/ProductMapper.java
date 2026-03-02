package com.micro_ecommerce.inventoryservice.mapper;

import com.micro_ecommerce.inventoryservice.dto.ProductReponseDto;
import com.micro_ecommerce.inventoryservice.dto.ProductRequestDto;
import com.micro_ecommerce.inventoryservice.entity.ProductEntity;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductReponseDto toProductResponseDto(ProductEntity productEntity);

    ProductEntity toProductEntity(ProductRequestDto productRequestDto);

    void updateProductEntityFromRequestDto(ProductRequestDto productRequestDto,
            @MappingTarget ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchProductEntityFromRequestDto(ProductRequestDto productRequestDto,
            @MappingTarget ProductEntity productEntity);

}
