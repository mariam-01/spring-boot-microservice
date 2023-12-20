package com.example.inventoryservice.datafetcher;


import com.example.inventoryservice.service.IServiceProduct;
import com.netflix.dgs.codegen.generated.types.ProductRequest;
import com.netflix.dgs.codegen.generated.types.ProductResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;
import java.util.Optional;

@DgsComponent

//@RequestMapping("/graphql/products")
public class ProductController {
    @Autowired
    IServiceProduct serviceProduct;

    //@GetMapping("/all")

    //@QueryMapping
    @DgsQuery
    /*public ResponseEntity<List<Product>> getAllProducts() {

        Optional<List<Product>> listProduct = serviceProduct.selectAllProducts();
        return listProduct
                .map(Products -> new ResponseEntity(Products, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    public List<ProductResponse> getAllProducts() {

        //Optional<List<Product>> listProduct = serviceProduct.selectAllProducts();
        Optional<List<ProductResponse>> listProduct = serviceProduct.selectAllProductsDto();

        return listProduct.get();
    }
    /*@DgsQuery
    public ProductResponse selectProductByID(@InputArgument int id) {

        Optional<ProductResponse> product = serviceProduct.selectProductsByID(id);

        return product.get();
    }*/


    @DgsMutation
    public String addProduct(@InputArgument ProductRequest productRequest) {

        return  serviceProduct.createProduct(productRequest);
    }

    @DgsMutation
    public String updateProduct(@Argument int id, @InputArgument ProductRequest productRequest) {

        return  serviceProduct.updateProduct(id,productRequest);
    }

    @DgsMutation
    public ProductResponse updateProductAndReturnIt(@Argument int id, @InputArgument ProductRequest productRequest) {

        return  serviceProduct.updateProductAndReturnIt(id,productRequest);
    }

    @DgsMutation
    public ProductResponse updateSpecifcAtrributeInProduct(@Argument int id, @InputArgument ProductRequest productRequest) {

        return  serviceProduct.updateSpecifcAtrributeInProduct(id,productRequest);
    }

    @DgsMutation
    public String deleteProduct(@InputArgument Integer id) {

        return  serviceProduct.deleteProduct(id);
    }


}
