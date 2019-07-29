package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity._Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao extends AbstractDao {

    public void addOrder(_Order order) {
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
    }

    public List<_Order> getAllOrders() {
        em.clear();
        return em.createQuery("FROM _Order", _Order.class)
                .getResultList();
    }

    public List<_Order> getRecentOrders() {
        return em.createQuery("FROM _Order o ORDER BY o.date DESC", _Order.class)
                .setMaxResults(100)
                .getResultList();
    }
}
