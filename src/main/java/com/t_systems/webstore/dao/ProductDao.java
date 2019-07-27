package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends AbstractDao{

    public void addProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public List<Product> getProductsByCat(String category) {
        List<Product> res = em.createQuery("FROM Product p WHERE p.category=(FROM Category c WHERE c.name=:category)",
                Product.class)
                .setParameter("category", category).getResultList();
        return res;
    }

    public void detachProduct(Product product)
    {
        em.getTransaction().begin();
        product.setCategory(null);
        product.setIngredients(null);
        product.setTags(null);
        em.merge(product);
        em.getTransaction().commit();
    }
}
