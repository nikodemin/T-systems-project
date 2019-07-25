package com.t_systems.webstore.service.api;

import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import com.t_systems.webstore.model.enums.Category;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    List<Product> getProductsByCategory(Category category);

    List<Product> getTopProducts();

    void addIngredient(Ingredient ingredient);

    void addTag(Tag tag);

    List<Tag> getAllTags();
}
