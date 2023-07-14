package com.example.demo;

import com.example.demo.model.CarModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarModelTests {

    @Test
    public void testCarModelProperties() {
        CarModel carModel = new CarModel();
        carModel.setCity("City");
        carModel.setType("Type");
        carModel.setMark("Mark");
        carModel.setModel("Model");
        carModel.setMileage(100);
        carModel.setVolume(2.0);
        carModel.setTransmission("Transmission");
        carModel.setColor("Color");

        assertEquals("City", carModel.getCity());
        assertEquals("Type", carModel.getType());
        assertEquals("Mark", carModel.getMark());
        assertEquals("Model", carModel.getModel());
        assertEquals(100, carModel.getMileage());
        assertEquals(2.0, carModel.getVolume());
        assertEquals("Transmission", carModel.getTransmission());
        assertEquals("Color", carModel.getColor());
    }
}
