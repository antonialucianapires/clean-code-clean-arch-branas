package com.antonialucianapires.taxi_online.infrastructure;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonialucianapires.taxi_online.domain.Account;
import com.antonialucianapires.taxi_online.domain.AccountResponseDTO;
import com.antonialucianapires.taxi_online.domain.GetAccountUseCase;
import com.antonialucianapires.taxi_online.domain.exception.AccountNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final GetAccountUseCase getAccountUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable UUID id) {
        log.debug("Searching for account with id {}", id);
        try {
            Account account = getAccountUseCase.execute(id);
            log.debug("Account found with ID: {}", id);
            return ResponseEntity.ok(new AccountResponseDTO(account));
        } catch (AccountNotFoundException exception) {
            log.debug("No account found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
