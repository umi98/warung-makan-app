package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.PathApi;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderService;
import com.enigmacamp.warung_makan_bahari_api.util.ResponseBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathApi.ORDER)
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final ResponseBuilderUtil responseBuilderUtil;
//    private final OrderRequest orderRequest;

    @PostMapping
    public ResponseEntity<CommonResponse<OrderResponse>> addNewOrder (@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.addNewTransaction(orderRequest);
        return responseBuilderUtil.buildResponse(orderResponse, "Successfully add new order", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderResponse> orderResponse = orderService.getAllOrders();
        return responseBuilderUtil.buildResponse(orderResponse, "Successfully retrieve all data", HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getOrderByCustomer(@PathVariable String id) {
        List<OrderResponse> orderResponses = orderService.getOrderByCustomerId(id);
        return responseBuilderUtil.buildResponse(orderResponses, "Successfully retrieve all data", HttpStatus.OK);
    }

    @GetMapping(PathApi.ID)
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        OrderResponse orderResponse = orderService.getOrderById(id);
        return responseBuilderUtil.buildResponse(orderResponse, "Successfully retrieve all data", HttpStatus.OK);
    }

    @GetMapping(PathApi.DETAIL_ID)
    public ResponseEntity<?> getOrderDetailById(@PathVariable String id) {
        OrderDetailResponse orderDetailResponse = orderDetailService.getOrderDetailById(id);
        return responseBuilderUtil.buildResponse(orderDetailResponse, "Successfully retrieve data", HttpStatus.OK);
    }
}
