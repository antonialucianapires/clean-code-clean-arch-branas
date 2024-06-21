package com.antonialucianapires.taxi_online.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Account {
    private static final String NAME_REGEX = "^[a-zA-Z]+ [a-zA-Z]+$";
    private final UUID accountId;
    private final String name;
    private final Email email;
    private final CPF cpf;
    private final Car car;
    private final Password password;
    private final boolean isPassenger;
    private final boolean isDriver;

    @Builder
    protected Account(
        @NonNull UUID accountId, 
        @NonNull String name, 
        @NonNull Email email, 
        @NonNull CPF cpf, 
        @NonNull Car car, 
        @NonNull Password password, 
        boolean isPassenger, 
        boolean isDriver) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name format: " + name + ".");
        }
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.car = car;
        this.password = password;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
    }

    private boolean isValidName(String name) {
        return !name.isBlank() && name.matches(NAME_REGEX);
    }
}
