package com.t_systems.webstore.service.api;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.*;

public interface MappingService {
    ProductDto toProductDto(Product product);

    CategoryDto toCategoryDto(Category category);

    IngredientDto toIngredientDto(Ingredient ingredient);

    TagDto toTagDto(Tag tag);

    Product toProduct(Product product, ProductDto productDto) throws Exception;

    OrderDto toOrderDto(_Order order);

    Category toCategory(CategoryDto categoryDto, String path);

    Ingredient toIngredient(IngredientDto ingredientDto);

    Tag toTag(TagDto tagDto);
}
