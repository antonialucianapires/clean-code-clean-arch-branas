package com.antonialucianapires.taxi_online.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CPFTest {

    @Test
    void validate() {
        String[] validCPFs = {"97456321558", "71428793860", "87748248800"};
       assertAll("valid cpf",
       () -> assertNotNull(new CPF(validCPFs[0])),
       () -> assertNotNull(new CPF(validCPFs[1])),
       () -> assertNotNull(new CPF(validCPFs[2])));
    }

    @Test
    void invalidCPF() {
        String[] invalidCPFs = {"", "123456", "12345678901234567890", "11111111111"};
        assertAll("invalid cpf",
        () -> assertThrows(IllegalArgumentException.class, () -> new CPF(null)),
        () -> assertThrows(IllegalArgumentException.class, () -> new CPF(invalidCPFs[0])),
        () -> assertThrows(IllegalArgumentException.class, () -> new CPF(invalidCPFs[1])),
        () -> assertThrows(IllegalArgumentException.class, () -> new CPF(invalidCPFs[2])),
        () -> assertThrows(IllegalArgumentException.class, () -> new CPF(invalidCPFs[3])));
    }
}