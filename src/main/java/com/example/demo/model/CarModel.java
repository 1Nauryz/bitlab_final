package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class CarModel extends BaseModel {

    @Column(name = "image")
    private String image;

    @Column(name = "city")
    private String city;

    @Column(name = "type")
    private String type;

    @Column(name = "mark")
    private String mark;

    @Column(name = "model")
    private String model;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "volume")
    private double volume;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "color")
    private String color;

    @Column(name = "phone")
    private String phone;

    public String loadImage(){
        if (image == null || image.isEmpty()) {
            return "/storage/default.png";
        }
        return image;
    }
}
