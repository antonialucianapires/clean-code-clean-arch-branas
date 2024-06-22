package com.antonialucianapires.taxi_online.domain;

import java.util.Objects;
import java.util.Optional;

public class Email {
    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private final String value;

    public Email(String rawEmail) {
        if(!isValid(rawEmail))
            throw new IllegalArgumentException("Invalid email format: " + rawEmail + ".");
        this.value = rawEmail;
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    private boolean isValid(String rawEmail) {
        if (!Objects.nonNull(rawEmail) || rawEmail.isBlank()) return false;
        return rawEmail.matches(EMAIL_REGEX);
    }
}
