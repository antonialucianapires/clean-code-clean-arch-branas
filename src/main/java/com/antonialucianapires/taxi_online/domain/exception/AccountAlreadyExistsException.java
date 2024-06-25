package com.antonialucianapires.taxi_online.domain.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String email) {
        super("An account with email " + email + " already exists.");
    }
}