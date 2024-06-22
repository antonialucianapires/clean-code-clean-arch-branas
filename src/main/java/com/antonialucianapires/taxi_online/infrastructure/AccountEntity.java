package com.antonialucianapires.taxi_online.infrastructure;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @Column(name = "account_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "car_plate")
    private String carPlate;

    @Column(name = "password")
    private String password;

    @Builder.Default
    @Column(name = "is_passenger", nullable = false)
    private boolean isPassenger = false;

    @Builder.Default
    @Column(name = "is_driver", nullable = false)
    private boolean isDriver = false;
}
