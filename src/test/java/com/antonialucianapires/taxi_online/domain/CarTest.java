package com.antonialucianapires.taxi_online.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CarTest {

    @Test
    void validCarPlate() {
        String plate = "ABC1234";
        Car car = new Car(plate);
        assertEquals(plate, car.getPlate());
    }

    @Test
    void invalidCarPlate() {
        String[] invalidCarPlates = {"", "AB12345", "abcd123", "ABC123", "ABCD123", "1234567"};
        assertAll("invalid car plate",
        () -> assertThrows(IllegalArgumentException.class, () -> new Car(null)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Car(invalidCarPlates[0])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Car(invalidCarPlates[1])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Car(invalidCarPlates[2])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Car(invalidCarPlates[3])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Car(invalidCarPlates[4])),
        () -> assertThrows(IllegalArgumentException.class, () -> new Car(invalidCarPlates[5])));
    }
}
