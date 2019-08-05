package com.t_systems.webstore.service.api;

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

    List<Ingredient> getAllIngredients();

    void removeIngredient(String name);

    Ingredient getIngredient(String name);

    void addTag(Tag tag);

    List<Tag> getAllTags();

    List<Tag> getTagsByCategory(String category);

    void removeTag(String name);

    Tag getTag(String name);

    void addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategory(String name);

    void removeProduct(String productName);

    void removeCategory(String name);

    void addIngToProduct(String productName, String ingredient);

    void updateProduct(Product product);

    void removeIngredientFromProduct(String productName, String ingredient);

    void removeTagFromProduct(String productName, String tag);

    void addTagToProduct(String productName, String tag);

    Product getProduct(String name);
}
