package com.t_systems.webstore.service;

import com.t_systems.webstore.dao.OrderDao;
import com.t_systems.webstore.dao.UserDao;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final MappingService mappingService;

    public void addOrder(_Order order) {
        Integer sum = 0;
        for (Product product:order.getItems()) {
            sum += product.getPrice();
        }
        order.setTotal(sum);
        orderDao.addOrder(order);
    }

    public List<_Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public List<_Order> getRecentOrders() {
        return orderDao.getRecentOrders();
    }

    public void changeStatus(Long id, String newStatus) {
        OrderStatus status = mappingService.toOrderStatus(newStatus);
        orderDao.changeStatus(id,status);
    }

    public List<_Order> getOrdersByUser(String user) {
        return orderDao.getOrdersByUser(userDao.getUser(user));
    }
}
