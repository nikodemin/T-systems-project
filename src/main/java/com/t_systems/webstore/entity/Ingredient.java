package com.t_systems.webstore.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ingredient")
@Data
public class Ingredient
{
    @Column(unique = true, nullable = false)
    private String name;
    private Integer price;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
