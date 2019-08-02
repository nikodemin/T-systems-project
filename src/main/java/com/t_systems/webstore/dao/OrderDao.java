package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.model.enums.OrderStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;

    public void addOrder(_Order order) {
        em.persist(order);
    }

    public List<_Order> getAllOrders() {
        em.clear();
        return em.createQuery("FROM _Order o ORDER BY o.id", _Order.class)
                .getResultList();
    }

    public List<_Order> getRecentOrders() {
        return em.createQuery("FROM _Order o ORDER BY o.date DESC", _Order.class)
                .setMaxResults(50)
                .getResultList();
    }

    public void changeStatus(Long id, OrderStatus newStatus) {
        _Order order = em.createQuery("FROM _Order o WHERE o.id=:id", _Order.class)
                .setParameter("id",id).getSingleResult();
        order.setStatus(newStatus);
        em.merge(order);
    }
}
