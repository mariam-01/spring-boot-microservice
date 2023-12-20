package com.example.servicetest.mapper;

import com.example.servicetest.entity.OrderLineItems;
import com.netflix.dgs.codegen.generated.types.OrderLineItemsRequest;
import com.netflix.dgs.codegen.generated.types.OrderLineItemsResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderLineItemsMapper {

    OrderLineItemsResponse OrderLineItemsToOrderLineItemsResponse(OrderLineItems orderLineItems);
    OrderLineItems OrderLineItemsRequestToOrderLineItems(OrderLineItemsRequest orderLineItemsRequest);
}
