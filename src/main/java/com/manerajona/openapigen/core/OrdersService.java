package com.manerajona.openapigen.core;

import com.manerajona.openapigen.core.domain.Order;

import java.util.UUID;

public interface OrdersService {

    UUID createOrder(Order order);

    Order getOrder(UUID guid);
}