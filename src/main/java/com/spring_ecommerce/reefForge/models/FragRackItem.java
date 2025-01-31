package com.spring_ecommerce.reefForge.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("FRAGRACKITEM")
public class FragRackItem extends Item{
    private String size;
    private byte magnetNum;
    private  String colour;
}
