package com.example.servicetest.datafetcher;

import com.example.servicetest.service.OrderService;
import com.netflix.dgs.codegen.generated.types.OrderRequest;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@DgsComponent
public class OrderController {

    private final OrderService orderService;


    @DgsMutation
    public String placeOrder(@InputArgument OrderRequest orderRequest) {
        System.out.println("Placing Order ");
        return orderService.placeOrder(orderRequest);
    }



}
