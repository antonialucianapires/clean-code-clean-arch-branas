package com.antonialucianapires.taxi_online.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class AccountTest {
    private static final String VALID_NAME = "John Doe";
    private static final Email VALID_EMAIL = new Email("example@example.com");
    private static final UUID VALID_ACCOUNT_ID = UUID.nameUUIDFromBytes(VALID_EMAIL.getValue().get().getBytes());
    private static final CPF VALID_CPF = new CPF("97456321558");
    private static final Car VALID_CAR = new Car("ABC1234");
    private static final Password VALID_PASSWORD = new Password("Password123");
    private Account.AccountBuilder validAccountBuilder() {
        return Account.builder()
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .cpf(VALID_CPF)
                .car(VALID_CAR)
                .password(VALID_PASSWORD)
                .isPassenger(true)
                .isDriver(false);
    }

    @Test
    void assertNonNulls() {
        assertAll("non-null fields",
            () -> assertThrows(NullPointerException.class, () -> 
                validAccountBuilder().name(null).build()),
            () -> assertThrows(NullPointerException.class, () -> 
                validAccountBuilder().email(null).build()),
            () -> assertThrows(NullPointerException.class, () -> 
                validAccountBuilder().cpf(null).build()),
            () -> assertThrows(NullPointerException.class, () -> 
                validAccountBuilder().password(null).build()));
    }

    @Test
    void validAccount() {
        Account account = validAccountBuilder().build();
        assertAll("account",
            () -> assertEquals(VALID_ACCOUNT_ID, account.getAccountId()),
            () -> assertEquals(VALID_NAME, account.getName()),
            () -> assertEquals(VALID_EMAIL, account.getEmail()),
            () -> assertEquals(VALID_CPF, account.getCpf()),
            () -> assertEquals(VALID_CAR, account.getCar()),
            () -> assertEquals(VALID_PASSWORD, account.getPassword()),
            () -> assertTrue(account.isPassenger()),
            () -> assertFalse(account.isDriver()));
    }

    @Test
    void invalidName() {
        String invalidName = "John";
        assertThrows(IllegalArgumentException.class, () ->
            validAccountBuilder().name(invalidName).build());
    }

    @Test
    void emptyName() {
        String emptyName = "";
        assertThrows(IllegalArgumentException.class, () ->
            validAccountBuilder().name(emptyName).build());
    }
}