package com.manerajona.openapigen.core.domain;

import java.util.Set;
import java.util.UUID;

public record Order(UUID id, Consumer consumer, Set<OrderItem> items, OrderState state, String notes) {

    public Order(Consumer consumer, Set<OrderItem> items, String notes) {
        this(UUID.randomUUID(), consumer, items, OrderState.APPROVAL_PENDING, notes);
    }
}