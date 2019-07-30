package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductDao {

    @PersistenceContext
    private EntityManager em;

    public void addProduct(Product product) {
        em.persist(product);
    }

    public List<Product> getProductsByCat(String category) {
        em.clear();
        List<Product> res = em.createQuery("FROM Product p WHERE p.category=(FROM Category c WHERE c.name=:category)",
                Product.class)
                .setParameter("category", category).getResultList();
        return res;
    }

    public void detachProduct(Product product) {
        product.setCategory(null);
        product.setIngredients(null);
        product.setTags(null);
        em.merge(product);
    }

    public void removeIngredientFromProduct(Product product, Ingredient ingredient) {
        em.createNativeQuery("DELETE FROM product_ingredient WHERE product_id=? AND ingredient_id=?")
                .setParameter(1, product.getId()).setParameter(2, ingredient.getId())
                .executeUpdate();
    }

    public void removeTagFromProduct(Product product, Tag tag) {
        em.createNativeQuery("DELETE FROM product_tag WHERE product_id=? AND tag_id=?")
                .setParameter(1, product.getId()).setParameter(2, tag.getId())
                .executeUpdate();
    }

    public void addTagToProduct(Product product, Tag tag) {
        product.getTags().add(tag);
        em.merge(product);
    }

    public void addIngToProduct(Product product, Ingredient ingredient) {
        product.getIngredients().add(ingredient);
        em.merge(product);
    }

    public void updateProduct(Product product) {
        em.merge(product);
    }

    public Product getProduct(String name) {
        return em.createQuery("FROM Product p WHERE p.name=:name",Product.class)
                .setParameter("name",name).getSingleResult();
    }
}
