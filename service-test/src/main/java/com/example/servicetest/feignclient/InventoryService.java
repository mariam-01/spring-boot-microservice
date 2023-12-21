package com.example.servicetest.feignclient;

import com.example.servicetest.shared.ProductRequest;
import com.example.servicetest.shared.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
        name = "INVENTORY-SERVICE"
)
public interface InventoryService {


    @GetMapping( path= "/product/{id}")
    public Optional<ProductResponse> selectProductByID(@PathVariable(name = "id") int id) ;

    @PutMapping( path= "/product/update/{id}")
    public String updateProduct(@PathVariable(name = "id") int id, @RequestBody ProductRequest productRequest);


    }
