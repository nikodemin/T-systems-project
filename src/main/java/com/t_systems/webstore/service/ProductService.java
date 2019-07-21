package com.t_systems.webstore.service;

import com.t_systems.webstore.entity.Ingredient;
import com.t_systems.webstore.entity.Product;
import com.t_systems.webstore.entity.Tag;
import com.t_systems.webstore.model.Category;

import java.util.List;

public interface ProductService
{
    void addProduct(Product product);
    List<Product> getProductsByCategory(Category category);
    List<Product> getTopProducts();
    void addIngredient(Ingredient ingredient);
    void addTag(Tag tag);
    List<Tag> getAllTags();
}
