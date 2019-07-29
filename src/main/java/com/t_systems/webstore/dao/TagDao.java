package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagDao extends AbstractDao {

    private final ProductDao productDao;

    public void addTag(Tag tag) {
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
    }

    public List<Tag> getAllTags() {
        return em.createQuery("FROM Tag", Tag.class).getResultList();
    }

    public Tag getTag(String name) {
        return em.createQuery("FROM Tag t WHERE t.name=:name", Tag.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void removeTag(String name) {
        em.getTransaction().begin();
        Tag tag = getTag(name);
        em.createQuery("FROM Product p WHERE :tag IN elements(p.tags)",
                Product.class).setParameter("tag", tag)
                .getResultList()
                .forEach(p -> productDao.removeTagFromProduct(p, tag));
        em.remove(tag);
        em.getTransaction().commit();
    }
}
