package com.antonialucianapires.taxi_online.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignupUseCaseTest {
    private AccountRepository accountRepository;
    private SignupUseCase signupUseCase;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        signupUseCase = new SignupUseCase(accountRepository);
    }

    @Test
    void execute() {
        UUID id = UUID.fromString("d2ba7559-9cd9-4562-8b7d-c3d498098939");
        Email email = new Email("example@example.com");
        CPF cpf = new CPF("97456321558");
        Car car = new Car("ABC1234");
        Password password = new Password("Password123");
        Account accountToSave = Account.builder()
            .accountId(id)
            .name("John Doe")
            .email(email)
            .cpf(cpf)
            .car(car)
            .password(password)
            .isPassenger(true)
            .isDriver(false)
            .build();
        when(accountRepository.save(accountToSave)).thenReturn(accountToSave);

        Account accountResult = signupUseCase.execute(accountToSave);

        assertEquals(accountToSave, accountResult);
        verify(accountRepository).save(accountToSave);
    }
}
