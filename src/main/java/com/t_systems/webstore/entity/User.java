package com.t_systems.webstore.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User
{
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
