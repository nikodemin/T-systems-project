package com.t_systems.webstore.service.api;

import com.t_systems.webstore.model.entity._Order;

import java.util.List;

public interface OrderService {
    void addOrder(_Order order);

    List<_Order> getAllOrders();

    List<_Order> getRecentOrders();

    void changeStatus(Long id, String newStatus);

    List<_Order> getOrdersByUser(String user);
}
