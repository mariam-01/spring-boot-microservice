package com.example.inventoryservice.service;

import com.example.inventoryservice.repository.InventoryRepo;
import com.netflix.dgs.codegen.generated.types.InventoryResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class InventoryService {

    private final InventoryRepo inventoryRepository;

    @Transactional()
    @SneakyThrows

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Checking Inventory");
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory -> {
                            InventoryResponse inventoryResponse = new InventoryResponse();
                            inventoryResponse.setSkuCode(inventory.getSkuCode());
                            inventoryResponse.setIsInStock(inventory.getQuantity() > 0);
                            return inventoryResponse;
                        }
                )
                .collect(Collectors.toList());

    }


}
