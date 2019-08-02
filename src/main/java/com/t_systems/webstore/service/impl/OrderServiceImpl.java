package com.t_systems.webstore.service.impl;

import com.t_systems.webstore.dao.OrderDao;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.model.enums.OrderStatus;
import com.t_systems.webstore.service.api.MappingService;
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
    private final MappingService mappingService;

    @Override
    public void addOrder(_Order order) {
        Integer sum = 0;
        for (Product product:order.getItems()) {
            sum += product.getPrice();
        }
        order.setTotal(sum);
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

    @Override
    public void changeStatus(Long id, String newStatus) {
        OrderStatus status = mappingService.toOrderStatus(newStatus);
        orderDao.changeStatus(id,status);
    }
}
