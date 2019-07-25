package com.t_systems.webstore.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tag")
public class Tag {
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
