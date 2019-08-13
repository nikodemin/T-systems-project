package com.t_systems.webstore.service;

import com.t_systems.webstore.dao.*;
import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TagDto;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductDao productDao;
    private final IngredientDao ingredientDao;
    private final OrderDao orderDao;
    private final TagDao tagDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private MappingService mappingService;

    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public List<Product> getProductsByCategory(String category) {
        return productDao.getProductsByCat(category);
    }

    public List<ProductDto> getTopProductsDto() {
        //get sorted products from last orders
        List<Product> sortedProducts = orderDao.getRecentOrders().stream()
                .flatMap(o -> o.getItems().stream())
                .filter(p->p.getCategory() != null && p.getAuthor() == null)
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
                .limit(10).map(e -> mappingService.toProductDto(e.getKey())).collect(Collectors.toList());
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientDao.addIngredient(ingredient);
    }

    public List<IngredientDto> getAllIngredientDtos() {
        return ingredientDao.getAllIngredients().stream().map(e -> mappingService.toIngredientDto(e))
                .collect(Collectors.toList());
    }

    public void removeIngredient(String name) {
        ingredientDao.removeIngredient(name);
    }

    public Ingredient getIngredient(String name) {
        return ingredientDao.getIngredient(name);
    }

    public void addTag(Tag tag) {
        tagDao.addTag(tag);
    }

    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    public List<TagDto> getTagDtosByCategory(String category) {
        return tagDao.getTagsByCategory(category).stream()
                .map(t->mappingService.toTagDto(t)).collect(Collectors.toList());
    }

    public void removeTag(String name) {
        tagDao.removeTag(name);
    }

    public Tag getTag(String name) {
        return tagDao.getTag(name);
    }

    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    public List<CategoryDto> getAllCategoryDtos() {
        return categoryDao.getAllCategories().stream()
                .map(mappingService::toCategoryDto).collect(Collectors.toList());
    }

    public Category getCategory(String name) {
        return categoryDao.getCategory(name);
    }

    public void addIngToProduct(String productName, String ingredient) {
        Product product = productDao.getProduct(productName);
        productDao.addIngToProduct(product, ingredientDao.getIngredient(ingredient));
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void detachOrRemoveProduct(String productName) {
        Product product = productDao.getProduct(productName);
        productDao.detachProduct(product);
        if (!orderDao.isProductInOrder(product)){
            productDao.removeProduct(product);
        }
    }

    //todo check if this behavior is normal
    public void removeCategory(String name) {
        Category category = categoryDao.getCategory(name);
        tagDao.removeCategory(category);
        ingredientDao.removeCategory(category);
        categoryDao.removeCategory(name);
    }

    public void removeIngredientFromProduct(String productName, String ingredient) {
        Product product = productDao.getProduct(productName);
        productDao.removeIngredientFromProduct(product, ingredientDao.getIngredient(ingredient));
    }

    public void removeTagFromProduct(String productName, String tag) {
        Product product = productDao.getProduct(productName);
        productDao.removeTagFromProduct(product, tagDao.getTag(tag));
    }

    public void addTagToProduct(String productName, String tag) {
        Product product = productDao.getProduct(productName);
        productDao.addTagToProduct(product, tagDao.getTag(tag));
    }

    public Product getProduct(String name) {
        return productDao.getProduct(name);
    }

    public List<IngredientDto> getIngredientDtosByCategory(String category) {
        return ingredientDao.getIngredientsByCategory(categoryDao.getCategory(category)).stream()
                .map(i->mappingService.toIngredientDto(i)).collect(Collectors.toList());
    }

    public List<ProductDto> getProductDtosByCategoryAndUser(String category, String username) {
        return productDao.getProductsByCatAndUser(categoryDao.getCategory(category), userDao.getUser(username))
                .stream().map(p->mappingService.toProductDto(p)).collect(Collectors.toList());
    }

    public List<ProductDto> getProductDtosWithTags(String category, List<TagDto> tags) {
        return getProductsByCategory(category).stream()
                .filter(cat->{
                    boolean res = true;
                    for (TagDto tag:tags) {
                        res &= cat.getTags().stream()
                                .filter(catTag->catTag.getName().equals(tag.getName()))
                                .count() > 0;
                    }
                    return res;
                })
                .map(p->mappingService.toProductDto(p)).collect(Collectors.toList());
    }

    public List<ProductDto> getProductDtosByCategory(String category) {
        return getProductsByCategory(category).stream()
                .map(p->mappingService.toProductDto(p)).collect(Collectors.toList());
    }

    public void setPrice(ProductDto productDto) {
        Integer total = 0;
        for (IngredientDto i:productDto.getIngredients()) {
            total += i.getPrice();
        }
        productDto.setPrice(total);
    }

    public List<TagDto> getAllTagDtos() {
        return getAllTags().stream().map(t -> mappingService.toTagDto(t))
                .collect(Collectors.toList());
    }

    public void setMappingService(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    public List<ProductDto> getTopProductsDtoForAdmin() {
        //get sorted products from last orders
        List<Product> sortedProducts = orderDao.getRecentOrders().stream()
                .flatMap(o -> o.getItems().stream())
                .filter(p->p.getCategory() != null)
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
                .limit(10).map(e -> {
                    ProductDto productDto = mappingService.toProductDto(e.getKey());
                    if (e.getKey().getAuthor() != null) {
                        productDto.setIsCustom(true);
                    }
                    return productDto;
                }).collect(Collectors.toList());
    }
}
