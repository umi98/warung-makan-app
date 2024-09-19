package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderDetailRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderRepository;
import com.enigmacamp.warung_makan_bahari_api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuService menuService;
    private final CustomerService customerService;
    private final TablesService tablesService;
    private final OrderDetailService orderDetailService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderResponse addNewTransaction(OrderRequest orderRequest) {
        Customer customer = customerService.getCustById(orderRequest.getCustomerId());
        Tables tables = tablesService.getById(orderRequest.getTableId());
        Order order = Order.builder()
                .customer(customer)
                .tables(tables)
                .transDate(LocalDateTime.now())
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
            Menu menu = menuService.getById(detailRequest.getMenuId());
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
        return result.stream().map(this::mapToResponse).toList();
    }

    @Override
    public OrderResponse getOrderById(String id) {
        Order result = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return mapToResponse(result);
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
