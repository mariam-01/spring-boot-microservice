package com.example.inventoryservice.datafetcher;

import com.example.inventoryservice.service.IServiceProduct;
import com.netflix.dgs.codegen.generated.types.ProductRequest;
import com.netflix.dgs.codegen.generated.types.ProductResponse;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")

public class ProductRestController {
    @Autowired
    IServiceProduct serviceProduct;

    @GetMapping( path= "/{id}")
    public Optional<ProductResponse> selectProductByID(@PathVariable(name = "id") int id) {

        Optional<ProductResponse> productRes = serviceProduct.selectProductsByID(id);

        return productRes;
    }

    @PutMapping( path= "/update/{id}")
    public String updateProduct(@PathVariable(name = "id") int id, @RequestBody ProductRequest productRequest) {

        return  serviceProduct.updateProduct(id,productRequest);
    }
}
