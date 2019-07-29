package com.t_systems.webstore.model.entity;

import lombok.Data;

import javax.persistence.*;

@Embeddable
@Table(name = "address")
@Data
public class Address {
    private String country;
    private String city;
    private String postCode;
    private String street;
    private String house;
    private String flat;
}
