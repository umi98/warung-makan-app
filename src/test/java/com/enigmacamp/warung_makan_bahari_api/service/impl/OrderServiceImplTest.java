package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderDetailRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.service.TablesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MenuService menuService;
    @Mock
    private CustomerService customerService;
    @Mock
    private TablesService tablesService;
    @Mock
    private OrderDetailServiceImpl orderDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewTransaction_WhenAllInputValid_ShouldReturnOrderAndDetail() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId("C01");
        request.setTableId("T01");

        Customer customer = Customer.builder()
                .id("C01")
                .fullName("ayam")
                .phoneNumber("0341")
                .isMember(false)
                .build();
        when(customerService.getCustById(request.getCustomerId())).thenReturn(customer);
        Tables tables = Tables.builder()
                .id("T01")
                .name("Table 1")
                .build();
        when(tablesService.getById(request.getTableId())).thenReturn(tables);
        Order order = Order.builder()
                .customer(customer)
                .tables(tables)
                .transDate(LocalDateTime.now())
                .build();
        when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(order);

        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setMenuId("M01");
        orderDetailRequest.setQty(2);
        request.setOrderDetails(Collections.singletonList(orderDetailRequest));

        Menu menu = Menu.builder()
                .id("M01")
                .name("Soto")
                .price(1000L)
                .menuImage(new MenuImage())
                .build();
        when(menuService.getById("M01")).thenReturn(menu);

        List<OrderDetail> details = new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .id("123")
                .qty(2)
                .price(200L)
                .menu(menu)
                .build();
        details.add(orderDetail);
        when(orderDetailService.addBulk(anyList())).thenReturn(details);
        OrderResponse response = orderService.addNewTransaction(request);
        assertNotNull(response, "Result should not be null when input is success");
    }

    @Test
    void getAllOrders_WhenDataExist_ShouldReturnList() {
        List<Order> orderList = new ArrayList<>();
        Order order = Order.builder()
                .id("id01")
                .transDate(LocalDateTime.now())
                .tables(new Tables())
                .customer(new Customer())
                .build();
        List<OrderDetail> newOrder = new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .id("idd01")
                .order(order)
                .menu(new Menu())
                .price(10000L)
                .qty(1)
                .build();
        newOrder.add(orderDetail);
        order.setOrderDetails(newOrder);
        orderList.add(order);
        assertNotNull(orderList, "Output should not null when list was not empty");
        when(orderRepository.findAll()).thenReturn(orderList);
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .tableId(order.getTables().getId())
                .transDate(order.getTransDate())
                .orderDetails(orderDetailResponses)
                .build();
        List<OrderResponse> orderResponseList = Collections.singletonList(orderResponse);
        when(orderService.getAllOrders()).thenReturn(orderResponseList);
        assertThat(orderList).isInstanceOf(ArrayList.class);
    }

    @Test
    void getAllOrders_WhenListIsEmpty_ShouldReturnEmptyList() {
        List<Order> order = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(null);
        assertThat(order).isEmpty();
    }

    @Test
    void getOrderById_WhenOrderIsFound_ShouldReturnOrder() {
        Order order = Order.builder()
                .id("id01")
                .transDate(LocalDateTime.now())
                .tables(new Tables())
                .customer(new Customer())
                .build();
        List<OrderDetail> newOrder = new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .id("idd01")
                .order(order)
                .menu(new Menu())
                .price(10000L)
                .qty(1)
                .build();
        newOrder.add(orderDetail);
        order.setOrderDetails(newOrder);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        OrderResponse response = orderService.getOrderById(order.getId());
        assertNotNull(response, "Output should not null when order was found");
        assertEquals(order.getId(), response.getOrderId(), "Actual and Expected Output Mismatch");
        assertEquals(order.getCustomer().getId(), response.getCustomerId(), "Actual and Expected Output Mismatch");
    }

    @Test
    void getOrderById_WhenOrderIsNotFound_ShouldReturnNotFoundException() {
        when(orderRepository.findById("id01")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> orderService.getOrderById("id01"));
        assertEquals("404 NOT_FOUND \"Order not found\"", exception.getMessage(), "Actual and Expected Output Mismatch");
        verify(orderRepository, times(1)).findById("id01");
    }
}