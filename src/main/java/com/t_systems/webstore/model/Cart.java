package com.t_systems.webstore.model;

import com.t_systems.webstore.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class Cart
{
    private List<Product> products;
}
