package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderDetailRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
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
import java.util.ArrayList;
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
    public Order addNewTransaction(OrderRequest orderRequest) {
        Order order = new Order();
        Tables tables = new Tables();
        tables.setId(orderRequest.getTableId());
        order.setTables(tables);
        Customer customer = new Customer();
        customer.setId(orderRequest.getCustomerId());
        order.setCustomer(customer);
        order.setTransDate(LocalDateTime.now());

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
            OrderDetail detail = new OrderDetail();

            Menu menu = menuService.getMenuById(detailRequest.getMenuId());
            menu.setId(detailRequest.getMenuId());

            detail.setMenu(menu);
            detail.setPrice(menu.getPrice());
            detail.setQty(detailRequest.getQty());

            orderDetails.add(detail);
        }

        order.setOrderDetails(orderDetails);
        orderRepository.saveAndFlush(order);
        orderDetailService.addBulk(order.getOrderDetails());
        for(OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrder(order);
        }

        return order;
    }

//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public Order addNewTransaction(OrderRequest orderRequest) {
//        return null;
//    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
