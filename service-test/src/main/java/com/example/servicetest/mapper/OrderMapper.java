package com.example.servicetest.mapper;

import com.example.servicetest.entity.Order;
import com.netflix.dgs.codegen.generated.types.OrderRequest;
import com.netflix.dgs.codegen.generated.types.OrderResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {
    OrderResponse OrderToOrderResponse(Order order);
    Order OrderRequestToOrder(OrderRequest orderRequest);
}
