package com.t_systems.webstore.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order
{
    @OneToOne
    private User client;
    private Boolean byCard; //or cash
    private String deliveryMethod;
    @OneToMany
    private List<Product> items;
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
