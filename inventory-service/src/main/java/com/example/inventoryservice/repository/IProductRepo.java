package com.example.inventoryservice.repository;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByIdIn(List<Integer> productsIds);

}
