package com.t_systems.webstore.model.dto;

import com.t_systems.webstore.model.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    private String name;
    private Integer price;
    private Integer spicy;
    private String image;
    private Category category;
    private List<TagDto> tags;
    private List<IngredientDto> ingredients;
}
