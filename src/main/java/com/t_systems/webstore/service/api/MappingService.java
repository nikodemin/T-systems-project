package com.t_systems.webstore.service.api;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TagDto;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;

public interface MappingService {
    ProductDto toProductDto(Product product);

    CategoryDto toCategoryDto(Category category);

    IngredientDto toIngredientDto(Ingredient ingredient);

    TagDto toTagDto(Tag tag);

    Product toProduct(Product product, ProductDto productDto) throws Exception;
}
