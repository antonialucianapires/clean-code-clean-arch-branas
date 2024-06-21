package com.antonialucianapires.taxi_online.domain;

import java.util.Objects;

public class Password {
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private final String value;

    public Password(String rawPassword) {
        if(!isValid(rawPassword)) 
            throw new IllegalArgumentException(
                "Password must contain at least one digit, " +
                "one lowercase letter, " +
                "one uppercase letter, " +
                "one alphanumeric character, and be at least " +
                "8 characters long.");
        this.value = rawPassword;
    }

    public String getValue() {
        return value;
    }

    private boolean isValid(String rawPassword) {
        if (!Objects.nonNull(rawPassword) || rawPassword.isBlank()) return false;
        return rawPassword.matches(PASSWORD_REGEX);
    }
}
