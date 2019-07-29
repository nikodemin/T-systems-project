package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends AbstractDao {

    public void addProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public List<Product> getProductsByCat(String category) {
        em.clear();
        List<Product> res = em.createQuery("FROM Product p WHERE p.category=(FROM Category c WHERE c.name=:category)",
                Product.class)
                .setParameter("category", category).getResultList();
        return res;
    }

    public void detachProduct(Product product) {
        em.getTransaction().begin();
        product.setCategory(null);
        product.setIngredients(null);
        product.setTags(null);
        em.merge(product);
        em.getTransaction().commit();
    }

    public void removeIngredientFromProduct(Product product, Ingredient ingredient) {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM product_ingredient WHERE product_id=? AND ingredient_id=?")
                .setParameter(1, product.getId()).setParameter(2, ingredient.getId())
                .executeUpdate();
        em.getTransaction().commit();
    }

    public void removeTagFromProduct(Product product, Tag tag) {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM product_tag WHERE product_id=? AND tag_id=?")
                .setParameter(1, product.getId()).setParameter(2, tag.getId())
                .executeUpdate();
        em.getTransaction().commit();
    }

    public void addTagToProduct(Product product, Tag tag) {
        em.getTransaction().begin();
        product.getTags().add(tag);
        em.merge(product);
        em.getTransaction().commit();
    }

    public void addIngToProduct(Product product, Ingredient ingredient) {
        em.getTransaction().begin();
        product.getIngredients().add(ingredient);
        em.merge(product);
        em.getTransaction().commit();
    }

    public void updateProduct(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }
}
