package com.t_systems.webstore.service;

import com.t_systems.webstore.dao.OrderDao;
import com.t_systems.webstore.dao.UserDao;
import com.t_systems.webstore.model.dto.OrderDto;
import com.t_systems.webstore.model.dto.TotalGainDto;
import com.t_systems.webstore.model.dto.UserDto;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.User;
import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<OrderDto> getOrderDtosByUser(String user) {
        return orderDao.getOrdersByUser(userDao.getUser(user)).stream()
                .map(o->mappingService.toOrderDto(o)).collect(Collectors.toList());
    }

    public List<OrderDto> getRecentOrderDtos() {
        return getRecentOrders().stream().map(o->mappingService.toOrderDto(o))
                .collect(Collectors.toList());
    }

    public TotalGainDto getTotalGainDto() {
        TotalGainDto res = new TotalGainDto();
        Integer forMonth = 0, forWeek = 0;

        LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);

        List<_Order> orders = getRecentOrders();
        for (_Order order:orders) {
            LocalDate orderDate = order.getDate().toInstant().
                    atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (!orderDate.isBefore(monthStart))
                forMonth += order.getTotal();
            if (!orderDate.isBefore(weekStart))
                forWeek += order.getTotal();
        }

        res.setMonth(forMonth);
        res.setWeek(forWeek);
        return res;
    }

    public List<UserDto> getTopClients() {
        Map<User,Integer> map = new HashMap<>();
        for (_Order order:getRecentOrders()) {
            User user = order.getClient();
            if (map.containsKey(user))
                map.put(user,map.get(user)+1);
            else
                map.put(user,1);
        }

        return map.entrySet().stream().sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))
                .limit(10).map(e->mappingService.toUserDto(e.getKey())).collect(Collectors.toList());
    }
}
