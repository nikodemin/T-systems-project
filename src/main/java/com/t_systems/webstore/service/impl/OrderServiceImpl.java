package com.t_systems.webstore.service.impl;

import com.t_systems.webstore.dao.OrderDao;
import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.service.api.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;

    @Override
    public void addOrder(_Order order) {
        orderDao.addOrder(order);
    }

    @Override
    public List<_Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public List<_Order> getRecentOrders() {
        return orderDao.getRecentOrders();
    }
}
