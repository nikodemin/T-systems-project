package com.t_systems.webstore.service.api;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    List<Product> getProductsByCategory(String category);

    List<Product> getTopProducts();

    void addIngredient(Ingredient ingredient);

    void addTag(Tag tag);

    List<Tag> getAllTags();

    void addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategory(String name);

    ProductDto toProductDto(Product product);

    CategoryDto toCategoryDto(Category category);

    void removeCategory(String name);
}
