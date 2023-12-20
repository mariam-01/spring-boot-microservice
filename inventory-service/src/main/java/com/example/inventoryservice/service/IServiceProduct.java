package com.example.inventoryservice.service;

import com.example.inventoryservice.entities.Product;
import com.netflix.dgs.codegen.generated.types.ProductRequest;
import com.netflix.dgs.codegen.generated.types.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface IServiceProduct {
    public String createProduct(ProductRequest productRequest) ;
    //public List<ProductDto> selectAllProducts();
    public Optional<List<ProductResponse>> selectAllProductsDto();


    Optional<ProductResponse> selectProductsByID(int id);

    Optional<List<Product>> selectAllProducts();

    String updateProduct(int id, ProductRequest productRequest);

    ProductResponse updateProductAndReturnIt(int id, ProductRequest productRequest);

    ProductResponse updateSpecifcAtrributeInProduct(int id, ProductRequest productRequest);

    String deleteProduct(int id);
}
