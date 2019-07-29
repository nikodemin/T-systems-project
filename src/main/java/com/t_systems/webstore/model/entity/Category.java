package com.t_systems.webstore.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category")
public class Category extends AbstractEntity{

    @Column(unique = true, nullable = false)
    private String name;
    private String image;
}
