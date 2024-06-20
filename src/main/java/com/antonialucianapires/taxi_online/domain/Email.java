package com.antonialucianapires.taxi_online.domain;

import java.util.Objects;

public class Email {
    private final String value;

    public Email(String rawEmail) {
        if(!isValid(rawEmail))
            throw new IllegalArgumentException("Email is invalid.");
        this.value = rawEmail;
    }

    public String getValue() {
        return value;
    }

    private boolean isValid(String rawEmail) {
        if (!Objects.nonNull(rawEmail) || rawEmail.isBlank()) return false;
        return rawEmail.matches("^(.+)@(.+)$");
    }
}
