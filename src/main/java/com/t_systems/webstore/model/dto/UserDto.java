package com.t_systems.webstore.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @NotBlank
    @Size(min = 3, max=30)
    private String firstName;
    @NotBlank
    @Size(min = 3, max=30)
    private String lastName;
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    @Size(min = 3, max=20)
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 7, max=20)
    private String password;
    @NotBlank
    @Size(min = 3, max=30)
    private String confirm;

    @NotBlank
    @Size(min = 3, max=30)
    private String country;
    @NotBlank
    @Size(min = 3, max=30)
    private String city;
    @NotBlank
    @Pattern(regexp = "[0-9]{6}")
    private String postCode;
    @NotBlank
    @Size(min = 3, max=30)
    private String street;
    @NotBlank
    @Size(min = 1, max=10)
    private String house;
    @NotBlank
    @Size(min = 1, max=10)
    private String flat;
}
