package com.t_systems.webstore.service;

import com.t_systems.webstore.entity._Order;

import java.util.List;

public interface OrderService
{
    void addOrder(_Order order);
    List<_Order> getAllOrders();
    List<_Order> getRecentOrders();
}
