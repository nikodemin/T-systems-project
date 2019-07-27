package com.t_systems.webstore.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category")
public class Category {

    @Column(unique = true, nullable = false)
    private String name;
    private String image;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
