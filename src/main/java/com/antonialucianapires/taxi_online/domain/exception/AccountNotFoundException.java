package com.antonialucianapires.taxi_online.domain.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(UUID id) {
        super("Account with ID " + id + " not found.");
    }
}