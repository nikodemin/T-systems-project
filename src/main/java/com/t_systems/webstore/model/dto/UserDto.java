package com.t_systems.webstore.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String username;
    private String email;
    private String password;
    private String confirm;

    private String country;
    private String city;
    private String postCode;
    private String street;
    private String house;
    private String flat;
}
