package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IngredientDao extends AbstractDao {

    private final ProductDao productDao;

    public void addIngredient(Ingredient ingredient) {
        em.getTransaction().begin();
        em.persist(ingredient);
        em.getTransaction().commit();
    }

    public List<Ingredient> getAllIngredients() {
        return em.createQuery("FROM Ingredient", Ingredient.class)
                .getResultList();
    }

    public Ingredient getIngredient(String name) {
        return em.createQuery("FROM Ingredient i WHERE i.name=:name", Ingredient.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void removeIngredient(String name) {
        em.getTransaction().begin();
        Ingredient ing = getIngredient(name);
        em.createQuery("FROM Product p WHERE :ingredient IN elements(p.ingredients)",
                Product.class).setParameter("ingredient", ing)
                .getResultList()
                .forEach(p -> productDao.removeIngredientFromProduct(p, ing));
        em.remove(ing);
        em.getTransaction().commit();
    }
}
