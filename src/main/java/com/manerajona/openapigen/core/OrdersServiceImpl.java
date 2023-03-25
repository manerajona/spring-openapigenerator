package com.manerajona.openapigen.core;

import com.manerajona.openapigen.NotFoundException;
import com.manerajona.openapigen.core.domain.Order;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final Set<Order> repository;

    public OrdersServiceImpl() {
        this.repository = new HashSet<>();
    }

    @Override
    public UUID createOrder(Order order) {
        repository.add(order);
        return order.id();
    }

    @Override
    public Order getOrder(UUID guid) {
        return repository.stream()
                .filter(order -> order.id().equals(guid))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}