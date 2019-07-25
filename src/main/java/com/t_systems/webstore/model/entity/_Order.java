package com.t_systems.webstore.model.entity;

import com.t_systems.webstore.model.enums.DeliveryMethod;
import com.t_systems.webstore.model.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class _Order {
    @ManyToOne
    private User client;
    private Boolean byCard; //or cash
    private DeliveryMethod deliveryMethod;
    private Date date;
    @ManyToMany
    private List<Product> items;
    private OrderStatus status;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
