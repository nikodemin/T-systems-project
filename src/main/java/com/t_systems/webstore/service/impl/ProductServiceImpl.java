package com.t_systems.webstore.service.impl;

import com.t_systems.webstore.dao.*;
import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TagDto;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final IngredientDao ingredientDao;
    private final OrderDao orderDao;
    private final TagDao tagDao;
    private final CategoryDao categoryDao;
    private final ModelMapper modelMapper;

    @Override
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productDao.getProductsByCat(category);
    }

    @Override
    public List<Product> getTopProducts() {
        //get sorted products from last orders
        List<Product> sortedProducts = orderDao.getRecentOrders().stream()
                .flatMap(o -> o.getItems().stream())
                .sorted((p1, p2) -> p1.getId().compareTo(p2.getId()))
                .collect(Collectors.toList());

        //map of product and it quantity
        Map<Product, Integer> map = new HashMap<>();
        sortedProducts.forEach(p -> {
            if (map.containsKey(p))
                map.put(p, map.get(p) + 1);
            else
                map.put(p, 1);
        });

        //get top products related to it quantity
        return map.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(6).map(e -> e.getKey()).collect(Collectors.toList());
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredientDao.addIngredient(ingredient);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientDao.getAllIngredients();
    }

    @Override
    public void removeIngredient(String name) {
        ingredientDao.removeIngredient(name);
    }

    @Override
    public Ingredient getIngredient(String name) {
        return ingredientDao.getIngredient(name);
    }

    @Override
    public void addTag(Tag tag) {
        tagDao.addTag(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    @Override
    public void removeTag(String name) {
        tagDao.removeTag(name);
    }

    @Override
    public Tag getTag(String name) {
        return tagDao.getTag(name);
    }

    @Override
    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Category getCategory(String name) {
        return categoryDao.getCategory(name);
    }

    @Override
    public ProductDto toProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public IngredientDto toIngredientDto(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    @Override
    public TagDto toTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public void addIngToProduct(Product product, String ingredient) {
        productDao.addIngToProduct(product, ingredientDao.getIngredient(ingredient));
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public void removeCategory(String name) {
        categoryDao.removeCategory(name);
    }

    @Override
    public void removeIngredientFromProduct(Product product, String ingredient) {
        productDao.removeIngredientFromProduct(product, ingredientDao.getIngredient(ingredient));
    }

    @Override
    public void removeTagFromProduct(Product product, String tag) {
        productDao.removeTagFromProduct(product, tagDao.getTag(tag));
    }

    @Override
    public void addTagToProduct(Product product, String tag) {
        productDao.addTagToProduct(product, tagDao.getTag(tag));
    }
}
