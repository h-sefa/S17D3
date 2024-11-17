package com.workintech.zoo.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kangaroo {
    //id, name, height, weight, gender, isAggressive
    private int id;
    private String name;
    private Double height;
    private Double weight;
    private String gender;
    private  Boolean isAggressive;

    public Kangaroo(Kangaroo kangaroo){
        this.height = kangaroo.getHeight();
        this.weight = kangaroo.getWeight();
        this.gender = kangaroo.getGender();
        this.isAggressive = kangaroo.getIsAggressive();
        this.name = kangaroo.getName();
        this.id = kangaroo.getId();
    }



}
