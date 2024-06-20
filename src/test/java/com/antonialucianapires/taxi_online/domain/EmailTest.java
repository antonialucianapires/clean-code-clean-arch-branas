package com.antonialucianapires.taxi_online.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    void validEmail() {
        String rawEmail = "email@email.com";
        Email email = new Email(rawEmail);
        assertEquals(rawEmail, email.getValue());
    }

    @Test
    void invalidEmail() {
        String[] invalidEmails = {"", "email", "email@"};
        assertAll("invalid email",
        () -> assertThrows(IllegalArgumentException.class, () -> new Email(null)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmails[0])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmails[1])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmails[2])));
    }
}
