package com.t_systems.webstore.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tag")
public class Tag extends AbstractEntity{
    private String name;
}
