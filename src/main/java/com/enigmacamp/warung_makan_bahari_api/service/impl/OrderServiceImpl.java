package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderDetailRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderDetailRepository;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderRepository;
import com.enigmacamp.warung_makan_bahari_api.service.*;
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
    private final CustomerService customerService;
    private final TablesService tablesService;
    private final OrderDetailService orderDetailService;

//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public Order addNewTransaction(OrderRequest orderRequest) {
//        Order order = new Order();
//        Tables tables = new Tables();
//        tables.setId(orderRequest.getTableId());
//        order.setTables(tables);
//        Customer customer = new Customer();
//        customer.setId(orderRequest.getCustomerId());
//        order.setCustomer(customer);
//        order.setTransDate(LocalDateTime.now());
//
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
//            OrderDetail detail = new OrderDetail();
//
//            Menu menu = menuService.getMenuById(detailRequest.getMenuId());
//            menu.setId(detailRequest.getMenuId());
//
//            detail.setMenu(menu);
//            detail.setPrice(menu.getPrice());
//            detail.setQty(detailRequest.getQty());
//
//            orderDetails.add(detail);
//        }
//
//        order.setOrderDetails(orderDetails);
//        orderRepository.saveAndFlush(order);
//        orderDetailService.addBulk(order.getOrderDetails());
//        for(OrderDetail orderDetail : order.getOrderDetails()) {
//            orderDetail.setOrder(order);
//        }
//
//        return order;
//    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderResponse addNewTransaction(OrderRequest orderRequest) {
        Customer customer = customerService.getCustomerById(orderRequest.getCustomerId());
        Tables tables = tablesService.getTablesById(orderRequest.getTableId());
        Order order = Order.builder()
                .customer(customer)
                .tables(tables)
                .transDate(LocalDateTime.now())
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
            Menu menu = menuService.getMenuById(detailRequest.getMenuId());
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .menu(menu)
                    .qty(detailRequest.getQty())
                    .price(menu.getPrice())
                    .build();
            orderDetails.add(orderDetail);
        }

        orderDetailService.addBulk(orderDetails);
        order.setOrderDetails(orderDetails);

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> result = orderRepository.findAll();
        // cara 1
//        List<OrderResponse> responses = new ArrayList<>();
//        for (Order res: result) {
//            OrderResponse orderResponse = mapToResponse(res);
//            responses.add(orderResponse);
//        }
        // cara 2
        List<OrderResponse> responses = result.stream().map(this::mapToResponse).toList();
        return responses;
    }

    @Override
    public OrderResponse getOrderById(String id) {
        return null;
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderDetailResponse> response = order.getOrderDetails().stream().map(orderDetail -> {
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .orderId(orderDetail.getOrder().getId())
                    .menuId(orderDetail.getMenu().getId())
                    .price(orderDetail.getPrice())
                    .qty(orderDetail.getQty())
                    .build();
        }).toList();
        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .tableId(order.getTables().getId())
                .transDate(order.getTransDate())
                .orderDetails(response)
                .build();
    }
}
