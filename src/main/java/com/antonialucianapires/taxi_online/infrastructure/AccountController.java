package com.antonialucianapires.taxi_online.infrastructure;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonialucianapires.taxi_online.domain.Account;
import com.antonialucianapires.taxi_online.domain.AccountRequest;
import com.antonialucianapires.taxi_online.domain.AccountResponse;
import com.antonialucianapires.taxi_online.domain.GetAccountUseCase;
import com.antonialucianapires.taxi_online.domain.SignupUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Log4j2
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final SignupUseCase signupUseCase;
    private final GetAccountUseCase getAccountUseCase;

    @PostMapping("/signup")
    public ResponseEntity<AccountResponse> signup(@RequestBody AccountRequest accountRequest) {
        Account newAccount = accountRequest.toAccount();
        Account savedAccount = signupUseCase.execute(newAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(savedAccount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable UUID id) {
        log.debug("Searching for account with id {}", id);
        Account account = getAccountUseCase.execute(id);
        log.debug("Account found with ID: {}", id);
        return ResponseEntity.ok(new AccountResponse(account));
    }
}
