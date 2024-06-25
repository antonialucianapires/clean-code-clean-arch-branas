package com.antonialucianapires.taxi_online.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.antonialucianapires.taxi_online.config.IntegrationTest;
import com.antonialucianapires.taxi_online.domain.Account;
import com.antonialucianapires.taxi_online.domain.AccountRepository;
import com.antonialucianapires.taxi_online.domain.AccountResponseDTO;
import com.antonialucianapires.taxi_online.domain.CPF;
import com.antonialucianapires.taxi_online.domain.Car;
import com.antonialucianapires.taxi_online.domain.Email;
import com.antonialucianapires.taxi_online.domain.Password;

@Testcontainers
public class AccountControllerTest extends IntegrationTest {

    private static final Email VALID_EMAIL = new Email("alice.smith@example.com");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void getAccountById() {
        UUID accountId = UUID.nameUUIDFromBytes(VALID_EMAIL.getValue().get().getBytes());
        Account expected = createExpectedAccount(accountId);
        accountRepository.save(expected);
        ResponseEntity<AccountResponseDTO> result = getAccountResponse(accountId);
        assertAccountResponse(result, expected);
    }

    @Test
    void getAccountByIdNotFound() {
        UUID nonExistentAccountId = UUID.randomUUID();
        ResponseEntity<AccountResponseDTO> result = getAccountResponse(nonExistentAccountId);
        assertNotFoundResponse(result);
    }

    private Account createExpectedAccount(UUID accountId) {
        return Account.builder()
                .name("Alice Smith")
                .email(VALID_EMAIL)
                .cpf(new CPF("97456321558"))
                .car(new Car("DEF2345"))
                .password(new Password("Password456"))
                .isPassenger(true)
                .isDriver(false)
                .build();
    }

    private ResponseEntity<AccountResponseDTO> getAccountResponse(UUID accountId) {
        return restTemplate.getForEntity("/api/accounts/" + accountId, AccountResponseDTO.class);
    }

    private void assertAccountResponse(ResponseEntity<AccountResponseDTO> response, Account expectedAccount) {
        AccountResponseDTO actualAccountResponse = response.getBody();
        AccountResponseDTO expectedAccountResponse = new AccountResponseDTO(expectedAccount);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(actualAccountResponse).isEqualTo(expectedAccountResponse);
    }

    private void assertNotFoundResponse(ResponseEntity<AccountResponseDTO> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}
