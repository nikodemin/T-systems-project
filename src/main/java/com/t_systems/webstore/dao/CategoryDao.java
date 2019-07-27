package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao extends AbstractDao {

    @Autowired
    private ProductDao productDao;

    public void addCategory(Category category){
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
    }

    public Category getCategory(String name){
        return em.createQuery("FROM Category c WHERE c.name=:name",Category.class)
                .setParameter("name",name).getSingleResult();
    }

    public List<Category> getAllCategories()
    {
        return em.createQuery("FROM Category", Category.class)
                .getResultList();
    }

    public void removeCategory(String name)
    {
        em.getTransaction().begin();
        productDao.getProductsByCat(name).forEach(p->productDao.detachProduct(p));
        em.remove(getCategory(name));
        em.getTransaction().commit();
    }
}
