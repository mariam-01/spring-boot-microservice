package com.example.inventoryservice.datafetcher;

import com.example.inventoryservice.service.InventoryService;
import com.netflix.dgs.codegen.generated.types.InventoryResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@DgsComponent
public class InventoryController {

    private final InventoryService inventoryService;
    @DgsQuery

    public List<InventoryResponse> isInStock(@InputArgument List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }
}
