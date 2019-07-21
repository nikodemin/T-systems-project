package com.t_systems.webstore.dao;

import com.t_systems.webstore.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class TagDao
{
    @PersistenceUnit
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init()
    {
        em = emf.createEntityManager();
    }

    public void addTag(Tag tag)
    {
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
    }

    public List<Tag> getAllTags()
    {
        return em.createQuery("FROM Tag",Tag.class).getResultList();
    }
}
