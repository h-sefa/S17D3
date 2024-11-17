package com.workintech.zoo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Koala {
    //id, name, weight, sleepHour, gender
    private int id;
    private String name;
    private Double weight;
    private Double sleepHour;
    private String gender;
}

