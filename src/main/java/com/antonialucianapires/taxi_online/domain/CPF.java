package com.antonialucianapires.taxi_online.domain;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class CPF {
    private final int CPF_LENGTH = 11;
    private final int FACTOR_FIRST_DIGIT = 10;
    private final int FACTOR_SECOND_DIGIT = 11;
    private final String value;

    public CPF(String rawCPF) {
        if(!isValid(rawCPF)) 
            throw new IllegalArgumentException();
        this.value = rawCPF;
    }

    private boolean isValid(String rawCPF) {
        if (!Objects.nonNull(rawCPF) || rawCPF.isBlank()) return false;
        String cpf = removeNonDigits(rawCPF);
        if(cpf.length() != CPF_LENGTH) return false;
        if(allDigitsTheSame(cpf)) return false;
        int digit1 = calculateDigit(cpf, FACTOR_FIRST_DIGIT);
        int digit2 = calculateDigit(cpf, FACTOR_SECOND_DIGIT);
        return extractActualDigit(cpf).equals(String.valueOf(digit1).concat(String.valueOf(digit2)));
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    private String removeNonDigits(String rawCPF) {
        return rawCPF.replaceAll("\\D+", "");
    }

    private boolean allDigitsTheSame(String cpf) {
        char firstDigit = cpf.charAt(0);
        return IntStream.range(0, cpf.length())
                .allMatch(i -> cpf.charAt(i) == firstDigit);
    }

    private int calculateDigit(String cpf, int factor) {
        int total = IntStream.range(0, cpf.length())
                .filter(i -> factor - i > 1)
                .map(i -> Character.getNumericValue(cpf.charAt(i)) * (factor - i))
                .sum();
        int remainder = total % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    public static String extractActualDigit(String cpf) {
        return cpf.substring(9);
    }    
}
