package com.t_systems.webstore.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient")
@Data
public class Ingredient extends AbstractEntity{
    @Column(unique = true, nullable = false)
    private String name;
    private Integer price;
}
