package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientDao extends AbstractDao{

    public void addIngredient(Ingredient ingredient) {
        em.getTransaction().begin();
        em.persist(ingredient);
        em.getTransaction().commit();
    }
}
