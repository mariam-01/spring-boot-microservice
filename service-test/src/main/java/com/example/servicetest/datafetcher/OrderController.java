package com.example.servicetest.datafetcher;

import com.example.servicetest.service.OrderService;
import com.netflix.dgs.codegen.generated.types.OrderRequest;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@DgsComponent
@Slf4j
@PreAuthorize("isFullyAuthenticated()")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('client_admin')")
    @DgsMutation
    public String placeOrder(@InputArgument OrderRequest orderRequest) {
        log.warn("------------Placing Order-----------------");
        return orderService.placeOrder(orderRequest);
    }



}
