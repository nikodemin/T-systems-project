package com.t_systems.webstore.dao;

import com.t_systems.webstore.entity.Ingredient;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Repository
public class IngredientDao
{
    @PersistenceUnit
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init()
    {
        em = emf.createEntityManager();
    }

    public void addIngredient(Ingredient ingredient)
    {
        em.getTransaction().begin();
        em.persist(ingredient);
        em.getTransaction().commit();;
    }
}
