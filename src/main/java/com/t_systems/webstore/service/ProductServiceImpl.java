package com.t_systems.webstore.service;

import com.t_systems.webstore.dao.IngredientDao;
import com.t_systems.webstore.dao.OrderDao;
import com.t_systems.webstore.dao.ProductDao;
import com.t_systems.webstore.dao.TagDao;
import com.t_systems.webstore.entity.Ingredient;
import com.t_systems.webstore.entity.Product;
import com.t_systems.webstore.entity.Tag;
import com.t_systems.webstore.model.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService
{
    private final ProductDao productDao;
    private final IngredientDao ingredientDao;
    private final OrderDao orderDao;
    private final TagDao tagDao;

    @Override
    public void addProduct(Product product)
    {
        productDao.addProduct(product);
    }

    @Override
    public List<Product> getProductsByCategory(Category category)
    {
        return productDao.getProductsByCat(category);
    }

    @Override
    public List<Product> getTopProducts()
    {
        List<Product> sortedProducts = orderDao.getRecentOrders().stream()
                .flatMap(o->o.getItems().stream())
                .sorted((p1,p2)->p1.getId().compareTo(p2.getId()))
                .collect(Collectors.toList());

        Map<Product,Integer> map = new HashMap<>();
        sortedProducts.forEach(p->{
            if (map.containsKey(p))
                map.put(p,map.get(p)+1);
            else
                map.put(p,1);
        });

        return map.entrySet().stream().sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))
                .limit(6).map(e->e.getKey()).collect(Collectors.toList());
    }

    @Override
    public void addIngredient(Ingredient ingredient)
    {
        ingredientDao.addIngredient(ingredient);
    }

    @Override
    public void addTag(Tag tag)
    {
        tagDao.addTag(tag);
    }

    @Override
    public List<Tag> getAllTags()
    {
        return tagDao.getAllTags();
    }
}
