package com.example.inventoryservice.mapper;


import com.example.inventoryservice.entities.Product;
import com.netflix.dgs.codegen.generated.types.ProductRequest;
import com.netflix.dgs.codegen.generated.types.ProductResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component

public interface ProductMapper {
   // ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse productToProductResponse(Product product);

    Product productRequestToProduct(ProductRequest productRequest);
}
