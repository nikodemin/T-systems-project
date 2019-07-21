package com.t_systems.webstore.service;

import com.t_systems.webstore.dao.OrderDao;
import com.t_systems.webstore.entity._Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService
{
    private final OrderDao orderDao;

    @Override
    public void addOrder(_Order order)
    {
        orderDao.addOrder(order);
    }

    @Override
    public List<_Order> getAllOrders()
    {
        return orderDao.getAllOrders();
    }

    @Override
    public List<_Order> getRecentOrders()
    {
        return orderDao.getRecentOrders();
    }
}
