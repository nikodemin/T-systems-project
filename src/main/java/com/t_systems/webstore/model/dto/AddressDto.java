package com.t_systems.webstore.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressDto {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String house;
    @NotBlank
    private String flat;
}
