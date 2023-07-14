package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marks")
@Getter
@Setter
public class MarkModel extends BaseModel{

    @Column(name = "mark")
    private String mark;

    @Column(name = "icon")
    private String icon;
}
