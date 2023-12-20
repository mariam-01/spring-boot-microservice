package com.example.inventoryservice.service;


import com.example.inventoryservice.entities.Product;
import com.example.inventoryservice.mapper.ProductMapper;
import com.example.inventoryservice.repository.IProductRepo;
import com.netflix.dgs.codegen.generated.types.ProductRequest;
import com.netflix.dgs.codegen.generated.types.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceProduct implements IServiceProduct{
    // we can replace manual constructor injection with Spring's @Autowired annotation to inject the IProductRepo dependency

    private final IProductRepo productRepo ;

    //@Autowired
    private final ProductMapper productMapper;

    /*
     * public ProductService (IProductRepo iProductRepo) {
     * 	this.iProductRepo = iProductRepo;
     * }
     */

    @Override
    public String createProduct(ProductRequest productRequest) {

        Product product = productMapper.productRequestToProduct(productRequest);
        if (product != null){
            productRepo.save(product);
            return "Product has been created successfully";
        }
        else {
            return "Product is not saved";
        }
        //log.info("product {} is saved !", product.getId());

    }
    @Override
    public Optional<List<ProductResponse>> selectAllProductsDto(){
        List<Product> listProducts = productRepo.findAll();
        List<ProductResponse> listProductsDto =  listProducts.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
        return Optional.of(listProductsDto);
    }

    @Override
    public Optional<ProductResponse> selectProductsByID(int id){
        Optional<Product> product = productRepo.findById(id);
        ProductResponse productResponse =  productMapper.productToProductResponse(product.get());
        return Optional.of(productResponse);
    }

    @Override
    public Optional<List<Product>> selectAllProducts(){
        List<Product> listProducts = productRepo.findAll();

        return Optional.of(listProducts);
    }

    @Override
    public String updateProduct(int id, ProductRequest productRequest) {

        Optional<Product> productToUpdate = productRepo.findById(id);
        if (productToUpdate.isPresent() && productRequest != null){
            Product product = productMapper.productRequestToProduct(productRequest);
            product.setId(id);
            productRepo.save(product);
            return "Product has been updated successfully";
        }
        else {
            return "Product is not found or productRequest is null";
        }
    }




    @Override
    public ProductResponse updateProductAndReturnIt(int id, ProductRequest productRequest) {

        Optional<Product> productToUpdate = productRepo.findById(id);
        if (productToUpdate.isPresent() && productRequest != null){
            Product product = productMapper.productRequestToProduct(productRequest);
            product.setId(id);
            ProductResponse productResponse = productMapper.productToProductResponse(productRepo.save(product));
            return productResponse;
        }
        else {
            return null;
        }
    }

    @Override
    public ProductResponse updateSpecifcAtrributeInProduct(int id, ProductRequest productRequest) {

        Optional<Product> productToUpdate = productRepo.findById(id);
        System.out.println(productToUpdate);

        if (productToUpdate.isPresent() && productRequest != null){
            Product product = productMapper.productRequestToProduct(productRequest);
            product.setId(id);
            if (product.getName() == null){
                product.setName(productToUpdate.get().getName());
            }

            if (product.getDescription() == null){
                product.setDescription(productToUpdate.get().getDescription());
            }

            if (product.getPrice() == 0){
                product.setPrice(productToUpdate.get().getPrice());
            }

            if (product.getQuantity() == 0){
                product.setQuantity(productToUpdate.get().getQuantity());
            }

           /*if (Objects.equals(productRequest.getPrice(), null)) {
                Float existingPrice = productToUpdate.map(Product::getPrice).orElse(null);
                if (existingPrice == null) {
                    product.setPrice(product.getPrice());
                }
            }*/

            System.out.println(product);
            ProductResponse productResponse = productMapper.productToProductResponse(productRepo.save(product));
            return productResponse;
        }
        else {
            return null;
        }
    }

    @Override
    public String deleteProduct (int id){
        Optional<Product> productToDelete = productRepo.findById(id);
        if (productToDelete.isPresent()){
            productRepo.delete(productToDelete.get());
            return "Product has been deleted successfully";
        }
        else {
            return "Product is not found ";
        }
    }


    /*public boolean isInStock(List<Integer> productsIds) {
        log.info("Checking Inventory");
        List<Boolean> inStockList = productRepo.findByIdIn(productsIds)
                .stream()
                .map(product -> product.getQuantity() > 0)
                .collect(Collectors.toList());

        // If any product is not in stock, return false; otherwise, return true
        return inStockList.stream().allMatch(Boolean::booleanValue);
    }*/

    public boolean isInStock(Map<Integer, Integer> productQuantities) {

        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            Integer productId = entry.getKey();
            Integer orderQuantity = entry.getValue();

            Product product = productRepo.findById(productId).orElse(null);

            if (product == null || product.getQuantity() < orderQuantity) {
                // If the product is not found or has insufficient quantity, return false
                return false;
            }
        }

        // If all products have sufficient quantity, return true
        return true;
    }

}
