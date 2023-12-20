package com.example.inventoryservice.mapper;

import com.example.inventoryservice.entities.Inventory;
import com.netflix.dgs.codegen.generated.types.InventoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface InventoryMapper {

    InventoryResponse productToProductResponse(Inventory inventory);

    Inventory productRequestToProduct(InventoryResponse inventoryResponse);

}
