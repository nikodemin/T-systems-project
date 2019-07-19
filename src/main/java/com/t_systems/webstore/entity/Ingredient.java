package com.t_systems.webstore.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ingredient")
@Data
public class Ingredient
{
    private String name;
    private String price;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
