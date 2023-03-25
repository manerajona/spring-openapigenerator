package com.manerajona.openapigen.rs;

import com.manerajona.openapigen.core.OrdersService;
import com.manerajona.openapigen.core.domain.Order;
import com.manerajona.openapigen.rs.api.OrdersApi;
import com.manerajona.openapigen.rs.model.OrderRequest;
import com.manerajona.openapigen.rs.model.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class OrdersController implements OrdersApi {

    private final OrdersService service;
    private final OrdersControllerMapper mapper;

    public OrdersController(OrdersService service, OrdersControllerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> createOrder(OrderRequest orderRequest) {
        final UUID id = service.createOrder(
                mapper.orderRequestToOrder(orderRequest)
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<OrderResponse> getOrder(String id) {
        Order order = service.getOrder(
                UUID.fromString(id)
        );
        return ResponseEntity.ok(
                mapper.orderToOrderResponse(order)
        );
    }
}
