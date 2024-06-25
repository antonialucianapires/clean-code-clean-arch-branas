package com.antonialucianapires.taxi_online.domain.exception;

import java.util.UUID;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {
    private final UUID accountId;
    public AccountNotFoundException(UUID id) {
        super("Account with ID " + id + " not found.");
        this.accountId = id;
    }
}