package com.antonialucianapires.taxi_online.domain;

import java.util.UUID;

public record AccountResponse(
    UUID accountId,
    String name,
    String email,
    String cpf,
    String carPlate,
    boolean isPassenger,
    boolean isDriver) {

    public AccountResponse(Account account) {
        this(
            account.getAccountId(),
            account.getName(),
            account.getEmail().getValue().get(),
            account.getCpf().getValue().get(),
            account.getCar() != null ? account.getCar().getPlate() : null,
            account.isPassenger(),
            account.isDriver()
        );
    }
}