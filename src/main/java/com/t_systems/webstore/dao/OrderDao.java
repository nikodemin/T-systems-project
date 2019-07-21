package com.t_systems.webstore.dao;

import com.t_systems.webstore.entity._Order;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class OrderDao
{
    @PersistenceUnit
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init()
    {
        em = emf.createEntityManager();
    }

    public void addOrder(_Order order)
    {
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
    }

    public List<_Order> getAllOrders()
    {
        return em.createQuery("FROM _Order", _Order.class)
                .getResultList();
    }

    public List<_Order> getRecentOrders()
    {
        return em.createQuery("FROM _Order o ORDER BY o.date DESC", _Order.class)
                .setMaxResults(100)
                .getResultList();
    }
}
