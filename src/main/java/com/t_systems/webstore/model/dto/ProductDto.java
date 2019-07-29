package com.t_systems.webstore.model.dto;

import lombok.Data;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Data
public class ProductDto {

    private String name;
    private Integer price;
    private Integer spicy;
    private String image;
    private CategoryDto category;
    private CommonsMultipartFile[] files;
    private List<TagDto> tags;
    private List<IngredientDto> ingredients;
}
