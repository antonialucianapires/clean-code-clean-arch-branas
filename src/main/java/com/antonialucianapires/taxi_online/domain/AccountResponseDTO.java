package com.antonialucianapires.taxi_online.domain;

import java.io.Serializable;
import java.util.UUID;

public record AccountResponseDTO(
        UUID accountId,
        String name,
        String email,
        String cpf,
        String carPlate,
        boolean isPassenger,
        boolean isDriver) implements Serializable {

    public AccountResponseDTO(Account account) {
        this(
                account.getAccountId(),
                account.getName(),
                account.getEmail().getValue().get(),
                account.getCpf().getValue().get(),
                account.getCar() != null ? account.getCar().getPlate() : null,
                account.isPassenger(),
                account.isDriver());
    }
}