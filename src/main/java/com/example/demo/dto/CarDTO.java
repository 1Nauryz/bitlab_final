package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {

    private Long id;
    private String city;
    private String type;
    private String mark;
    private String model;
    private int mileage;
    private double volume;
    private String transmission;
    private String color;
    private String image;
}
