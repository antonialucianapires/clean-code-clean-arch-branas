package com.antonialucianapires.taxi_online.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    void validPassword() {
        String rawPassword = "P@ssw0rd123";
        Password email = new Password(rawPassword);
        assertEquals(rawPassword, email.getValue());
    }

    @Test
    void invalidPassword() {
        String[] invalidPasswords = {"", "PASSW0RD123", "passw0rd123"};
        assertAll("invalid password",
        () -> assertThrows(IllegalArgumentException.class, () -> new Password(null)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Password(invalidPasswords[0])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Password(invalidPasswords[1])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Password(invalidPasswords[2])));
    }
}
