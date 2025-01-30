package com.spring_ecommerce.himalayanfresh.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@DiscriminatorValue("COFFEEITEM")
public class CoffeeItem extends Item {

    @Column
    private String roastType;

    @Column
    private  String beanType;

}
