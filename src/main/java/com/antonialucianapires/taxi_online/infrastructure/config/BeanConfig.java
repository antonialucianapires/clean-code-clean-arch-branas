package com.antonialucianapires.taxi_online.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.antonialucianapires.taxi_online.domain.AccountRepository;
import com.antonialucianapires.taxi_online.domain.GetAccountUseCase;

@Configuration
public class BeanConfig {
    
    @Bean
    public GetAccountUseCase getAccountUseCase(AccountRepository accountRepository) {
        return new GetAccountUseCase(accountRepository);
    }
}
