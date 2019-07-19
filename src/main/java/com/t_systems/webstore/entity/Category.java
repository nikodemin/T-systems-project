package com.t_systems.webstore.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Data
public class Category
{
    @Column(unique = true, nullable = false)
    private String category;
    @Column(nullable = false)
    private String subCat;
    private String catImg;
    private String subCatImg;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
