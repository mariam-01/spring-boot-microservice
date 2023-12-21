package com.example.servicetest.service;

import com.example.servicetest.entity.Order;
import com.example.servicetest.feignclient.InventoryService;
import com.example.servicetest.mapper.OrderLineItemsMapper;
import com.example.servicetest.mapper.OrderMapper;
import com.example.servicetest.repo.OrderRepository;
import com.example.servicetest.shared.Product;
import com.example.servicetest.shared.ProductRequest;
import com.example.servicetest.shared.ProductResponse;
import com.netflix.dgs.codegen.generated.types.OrderLineItems;
import com.netflix.dgs.codegen.generated.types.OrderLineItemsRequest;
import com.netflix.dgs.codegen.generated.types.OrderRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineItemsMapper orderLineItemsMapper;
    private final InventoryService inventoryService;


    public String placeOrder(OrderRequest orderRequest) {

        Order order = orderMapper.OrderRequestToOrder(orderRequest);
        order.setOrderNumber(UUID.randomUUID().toString());

        List <OrderLineItemsRequest> orderLineRequestList = orderRequest.getOrderLineItemsListRequest();
        List <com.example.servicetest.entity.OrderLineItems> orderLineItemsList = orderLineRequestList
                    .stream()
                    .map(orderLineItemsMapper::OrderLineItemsRequestToOrderLineItems)
                     .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);


       /* List<Integer> productsIds= orderLineItemsList.stream()
                .map(OrderLineItems::getProductId)
                .toList();*/

        // Retrieve a Map of product ID and quantity from orderLineItemsList

        /*Map<Integer, Integer> productIdQuantityMap = orderLineItemsList.stream()
                .collect(Collectors.toMap(OrderLineItems::getProductId, OrderLineItems::getQuantity));*/

        Map<Integer, Integer> productsIdQuantityMap = orderLineItemsList.stream()
                .collect(Collectors.toMap(
                        orderLineItems -> orderLineItems.getProductId(),
                        orderLineItems -> orderLineItems.getQuantity()
                ));


        // Call Inventory Service and place order if products are in stock

        // i should now retreive  a Map of product ID and quantity from Inventory

       //()

        // isInStock is a method defined in inventory-service where I should compare between
        boolean allProductsInStock = isInStock(productsIdQuantityMap);
        if (allProductsInStock) {
            orderRepository.save(order);
            // i should update the qantity
            updateStock(productsIdQuantityMap);
            return "Order Placed";

        } else {
            //throw new IllegalArgumentException("Product is not in stock, please try again later");
            return "Product is not in stock, please try again later";
        }

    }

    public boolean isInStock(Map<Integer, Integer> productQuantities) {


        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            Integer productId = entry.getKey();
            Integer orderQuantity = entry.getValue();

            ProductResponse product = inventoryService.selectProductByID(productId).orElse(null);
            if (product == null || product.getQuantity() < orderQuantity) {
                // If the product is not found or has insufficient quantity, return false
                return false;
            }
        }

        // If all products have sufficient quantity, return true
        return true;
    }


    public void updateStock(Map<Integer, Integer> productQuantities) {


        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            Integer productId = entry.getKey();
            Integer orderQuantity = entry.getValue();

            ProductResponse productRes = inventoryService.selectProductByID(productId).orElse(null);
            productRes.setQuantity(productRes.getQuantity() - orderQuantity);

            ProductRequest product = new ProductRequest();

            product.setId(productRes.getId());
            product.setName(productRes.getName());
            product.setQuantity(productRes.getQuantity());
            product.setPrice(productRes.getPrice());
            product.setDescription(productRes.getDescription());
            inventoryService.updateProduct(productRes.getId(), product);

        }
    }




    }
