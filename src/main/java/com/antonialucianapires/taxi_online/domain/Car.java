package com.antonialucianapires.taxi_online.domain;

import java.util.Objects;

public class Car {
    private static final String PLATE_REGEX = "^[A-Z]{3}[0-9]{4}$";
    private final String plate;

    public Car(String plate) {
        if(!isValid(plate)) 
            throw new IllegalArgumentException("Invalid car plate format: " + plate + ".");
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    private boolean isValid(String plate) {
        if (!Objects.nonNull(plate) || plate.isBlank()) return false;
        return plate.matches(PLATE_REGEX);
    }
}
