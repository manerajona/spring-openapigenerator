package com.manerajona.openapigen.core.domain;

import java.util.UUID;

public record OrderItem(UUID id, String name, Integer quantity) {

    public OrderItem(String name, Integer quantity) {
        this(UUID.randomUUID(), name, quantity);
    }
}