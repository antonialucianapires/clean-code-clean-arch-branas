package com.antonialucianapires.taxi_online.domain;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Optional<Account> findById(UUID id);
}
