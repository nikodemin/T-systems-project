package com.t_systems.webstore.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String username;
    private String paymentMethod; //or cash
    private String deliveryMethod;
    private String date;
    private List<ProductDto> items;
    private String status;
}
