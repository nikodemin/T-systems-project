package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.enums.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends AbstractDao{

    public void addProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public List<Product> getProductsByCat(Category category) {
        return em.createQuery("FROM Product p WHERE p.category=:category", Product.class)
                .setParameter("category", category).getResultList();
    }
}