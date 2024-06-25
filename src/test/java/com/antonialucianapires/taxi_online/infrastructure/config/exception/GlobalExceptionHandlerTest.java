package com.antonialucianapires.taxi_online.infrastructure.config.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.antonialucianapires.taxi_online.domain.Account;
import com.antonialucianapires.taxi_online.domain.CPF;
import com.antonialucianapires.taxi_online.domain.Email;
import com.antonialucianapires.taxi_online.domain.GetAccountUseCase;
import com.antonialucianapires.taxi_online.domain.Password;
import com.antonialucianapires.taxi_online.domain.SignupUseCase;
import com.antonialucianapires.taxi_online.domain.exception.AccountAlreadyExistsException;
import com.antonialucianapires.taxi_online.domain.exception.AccountNotFoundException;
import com.antonialucianapires.taxi_online.infrastructure.AccountController;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AccountController.class)
@EnableWebMvc
public class GlobalExceptionHandlerTest {
    @Autowired private WebApplicationContext webApplicationContext;
    @Autowired private MockMvc mockMvc;
    @MockBean private SignupUseCase signupUseCase;
    @MockBean private GetAccountUseCase getAccountUseCase;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) webApplicationContext).build();
    }

    @Test
    void handleAccountAlreadyExistsException() throws Exception {
        Account existingAccount = createAccount();
        Mockito.when(signupUseCase.execute(Mockito.any(Account.class))).thenThrow(new AccountAlreadyExistsException(existingAccount.getEmail().getValue().get()));
        mockMvc.perform(post("/api/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jane Doe\",\"email\":\"jane.doe@example.com\",\"cpf\":\"97456321558\",\"password\":\"Password123!\",\"isPassenger\":true,\"isDriver\":false}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("An account with email jane.doe@example.com already exists."));
    }

    @Test
    void handleAccountNotFoundException() throws Exception {
        UUID nonExistentAccountId = UUID.randomUUID();
        Mockito.when(getAccountUseCase.execute(nonExistentAccountId)).thenThrow(new AccountNotFoundException(nonExistentAccountId));
        mockMvc.perform(get("/api/accounts/" + nonExistentAccountId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account with ID " + nonExistentAccountId + " not found."));
    }

    @Test
    void handleIllegalArgumentException() throws Exception {
        Mockito.when(signupUseCase.execute(Mockito.any(Account.class)))
                .thenThrow(new IllegalArgumentException("Invalid request data"));

        mockMvc.perform(post("/api/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"email\":\"invalid-email\",\"cpf\":\"\",\"password\":\"\",\"isPassenger\":true,\"isDriver\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid email format: invalid-email."));
    }

    @Test
    void handleException() throws Exception {
        Mockito.when(getAccountUseCase.execute(Mockito.any(UUID.class))).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(get("/api/accounts/trigger-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }

    private Account createAccount() {
        return Account.builder()
                .name("Jane Doe")
                .email(new Email("jane.doe@example.com"))
                .cpf(new CPF("97456321558"))
                .password(new Password("Password123!"))
                .isPassenger(true)
                .isDriver(false)
                .build();
    }
}