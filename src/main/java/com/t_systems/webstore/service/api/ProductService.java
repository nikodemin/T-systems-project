package com.t_systems.webstore.service.api;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TagDto;
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

    void removeTag(String name);

    Tag getTag(String name);

    void addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategory(String name);

    void removeCategory(String name);

    void removeIngredientFromProduct(Product product, String ingredient);

    void removeTagFromProduct(Product product, String tag);

    void addTagToProduct(Product product, String tag);

    ProductDto toProductDto(Product product);

    CategoryDto toCategoryDto(Category category);

    IngredientDto toIngredientDto(Ingredient ingredient);

    TagDto toTagDto(Tag tag);

    void addIngToProduct(Product product, String ingredient);

    void updateProduct(Product product);
}
