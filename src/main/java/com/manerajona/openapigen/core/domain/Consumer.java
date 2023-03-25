package com.manerajona.openapigen.core.domain;

import java.util.UUID;

public record Consumer(UUID id, String name, String address, String phone) {

    public Consumer(String name, String address, String phone) {
        this(UUID.randomUUID(), name, address, phone);
    }
}