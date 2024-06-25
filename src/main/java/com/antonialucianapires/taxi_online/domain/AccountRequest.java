package com.antonialucianapires.taxi_online.domain;

public record AccountRequest(
    String name,
    String email,
    String cpf,
    String carPlate,
    String password,
    boolean isPassenger,
    boolean isDriver) {

    public Account toAccount() {
        return Account.builder()
            .name(name)
            .email(new Email(email))
            .cpf(new CPF(cpf))
            .car(carPlate != null ? new Car(carPlate) : null)
            .password(new Password(password))
            .isPassenger(isPassenger)
            .isDriver(isDriver)
            .build();
    }
}
