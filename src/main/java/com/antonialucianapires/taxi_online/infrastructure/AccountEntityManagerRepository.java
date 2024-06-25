package com.antonialucianapires.taxi_online.infrastructure;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.antonialucianapires.taxi_online.domain.Account;
import com.antonialucianapires.taxi_online.domain.AccountRepository;
import com.antonialucianapires.taxi_online.domain.CPF;
import com.antonialucianapires.taxi_online.domain.Car;
import com.antonialucianapires.taxi_online.domain.Email;
import com.antonialucianapires.taxi_online.domain.Password;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class AccountEntityManagerRepository implements AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Account> findById(UUID id) {
        AccountEntity entity = entityManager.find(AccountEntity.class, id);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    @Override
    @Transactional
    public Account save(Account account) {
        AccountEntity accountEntity = toEntity(account);
        entityManager.persist(accountEntity);
        return account;
    }

    private Account toDomain(AccountEntity entity) {
        return Account.builder()
            .name(entity.getName())
            .email(new Email(entity.getEmail()))
            .cpf(new CPF(entity.getCpf()))
            .car(Objects.nonNull(entity.getCarPlate()) ? new Car(entity.getCarPlate()) : null)
            .password(new Password(entity.getPassword()))
            .isPassenger(entity.isPassenger())
            .isDriver(entity.isDriver())
            .build();
    }

    private AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
            .id(account.getAccountId())
            .name(account.getName())
            .email(account.getEmail().getValue().get())
            .cpf(account.getCpf().getValue().get())
            .carPlate(account.getCar() != null ? account.getCar().getPlate() : null)
            .password(account.getPassword().getValue())
            .isPassenger(account.isPassenger())
            .isDriver(account.isDriver())
            .build();
    }
}
