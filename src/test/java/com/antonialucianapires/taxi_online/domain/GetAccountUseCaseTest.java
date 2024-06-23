package com.antonialucianapires.taxi_online.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.antonialucianapires.taxi_online.domain.exception.AccountNotFoundException;

public class GetAccountUseCaseTest {
    private AccountRepository accountRepository;
    private GetAccountUseCase getAccountUseCase;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        getAccountUseCase = new GetAccountUseCase(accountRepository);
    }

    @Test
    void findById() {
        UUID id = UUID.fromString("d2ba7559-9cd9-4562-8b7d-c3d498098939");
        Email email = new Email("example@example.com");
        CPF cpf = new CPF("97456321558");
        Car car = new Car("ABC1234");
        Password password = new Password("Password123");
        Account accountExpected = Account.builder()
            .accountId(id)
            .name("John Doe")
            .email(email)
            .cpf(cpf)
            .car(car)
            .password(password)
        .build();
        when(accountRepository.findById(id)).thenReturn(Optional.of(accountExpected));
        Account accountResult = getAccountUseCase.execute(id);
        assertEquals(accountExpected, accountResult);
    }

    @Test
    void accountNotFound() {
        UUID id = UUID.fromString("d2ba7559-9cd9-4562-8b7d-c3d498098939");
        when(accountRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> getAccountUseCase.execute(id));
        verify(accountRepository).findById(id);
    }
}
