package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDao extends AbstractDao{

    public void addTag(Tag tag) {
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
    }

    public List<Tag> getAllTags() {
        return em.createQuery("FROM Tag", Tag.class).getResultList();
    }
}
