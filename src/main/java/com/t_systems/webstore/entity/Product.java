package com.t_systems.webstore.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product
{
    private String name;
    private String price;
    private Integer spicy;
    private String category;
    @OneToMany
    private List<Tag> tags;
    @OneToMany
    private List<Ingredient> ingredients;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
