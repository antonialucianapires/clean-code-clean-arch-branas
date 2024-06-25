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
import com.antonialucianapires.taxi_online.domain.AccountRequest;
import com.antonialucianapires.taxi_online.domain.AccountResponse;
import com.antonialucianapires.taxi_online.domain.CPF;
import com.antonialucianapires.taxi_online.domain.Car;
import com.antonialucianapires.taxi_online.domain.Email;
import com.antonialucianapires.taxi_online.domain.Password;
import com.antonialucianapires.taxi_online.infrastructure.config.exception.ErrorResponse;

@Testcontainers
public class AccountControllerTest extends IntegrationTest {
    private static final Email VALID_EMAIL = new Email("alice.smith@example.com");
    @Autowired TestRestTemplate restTemplate;
    @Autowired private AccountRepository accountRepository;

    @Test
    void getAccountById() {
        UUID accountId = UUID.nameUUIDFromBytes(VALID_EMAIL.getValue().get().getBytes());
        Account expected = createExpectedAccount(accountId, "Alice Smith", VALID_EMAIL.getValue().get());
        accountRepository.save(expected);
        ResponseEntity<AccountResponse> response = getAccountResponse(accountId, AccountResponse.class);
        assertAccountResponse(response, expected);
    }

    @Test
    void getAccountByIdNotFound() {
        UUID nonExistentAccountId = UUID.randomUUID();
        ResponseEntity<ErrorResponse> response = getAccountResponse(nonExistentAccountId, ErrorResponse.class);
        assertNotFoundResponse(response, nonExistentAccountId);
    }

    @Test
    void signup() {
        AccountRequest request = createAccountRequest("John Doe", "john.doe@example.com");
        ResponseEntity<AccountResponse> response = createAccount(request);
        assertCreatedResponse(response);
    }

    @Test
    void signupAccountAlreadyExists() {
        Account existingAccount = createExpectedAccount(UUID.nameUUIDFromBytes("jane.doe@example.com".getBytes()), "Jane Doe", "jane.doe@example.com");
        accountRepository.save(existingAccount);
        AccountRequest request = createAccountRequest("Jane Doe", "jane.doe@example.com");
        ResponseEntity<ErrorResponse> response = createAccountExpectingConflict(request);
        assertConflictResponse(response, "jane.doe@example.com");
    }

    private Account createExpectedAccount(UUID accountId, String name, String email) {
        return Account.builder()
                .name(name)
                .email(new Email(email))
                .cpf(new CPF("97456321558"))
                .car(new Car("DEF2345"))
                .password(new Password("Password456"))
                .isPassenger(true)
                .isDriver(false)
                .build();
    }

    private AccountRequest createAccountRequest(String name, String email) {
        return new AccountRequest(name, email, "97456321558", "ABC1234", "Password123!", true, false);
    }

    private <T> ResponseEntity<T> getAccountResponse(UUID accountId, Class<T> responseType) {
        return restTemplate.getForEntity("/api/accounts/" + accountId, responseType);
    }

    private ResponseEntity<AccountResponse> createAccount(AccountRequest request) {
        return restTemplate.postForEntity("/api/accounts/signup", request, AccountResponse.class);
    }

    private ResponseEntity<ErrorResponse> createAccountExpectingConflict(AccountRequest request) {
        return restTemplate.postForEntity("/api/accounts/signup", request, ErrorResponse.class);
    }

    private void assertAccountResponse(ResponseEntity<AccountResponse> response, Account expectedAccount) {
        AccountResponse actualAccountResponse = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualAccountResponse).isEqualTo(new AccountResponse(expectedAccount));
    }

    private void assertNotFoundResponse(ResponseEntity<ErrorResponse> response, UUID accountId) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Account with ID " + accountId + " not found.");
    }

    private void assertCreatedResponse(ResponseEntity<AccountResponse> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    private void assertConflictResponse(ResponseEntity<ErrorResponse> response, String email) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("An account with email " + email + " already exists.");
    }
}