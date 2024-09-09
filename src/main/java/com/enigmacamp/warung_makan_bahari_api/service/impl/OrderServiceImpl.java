package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.entity.OrderDetail;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderDetailRepository;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuService menuService;
    private final OrderDetailService orderDetailService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order addNewTransaction(Order order) {
        orderDetailService.addBulk(order.getOrderDetails());
        order.setTransDate(LocalDateTime.now());
        orderRepository.saveAndFlush(order);
        for(OrderDetail orderDetail : order.getOrderDetails()) {
            Menu menu = menuService.getMenuById(orderDetail.getMenu().getId());
            orderDetail.setOrder(order);
            orderDetail.setPrice(menu.getPrice());
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
