package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.PathApi;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderService;
import com.enigmacamp.warung_makan_bahari_api.util.ResponseBuilder;
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
    private final ResponseBuilder responseBuilder;
//    private final OrderRequest orderRequest;

    @PostMapping
    public ResponseEntity<CommonResponse<OrderResponse>> addNewOrder (@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.addNewTransaction(orderRequest);
        return responseBuilder.buildResponse(orderResponse, "Successfully add new order", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderResponse> orderResponse = orderService.getAllOrders();
        return responseBuilder.buildResponse(orderResponse, "Successfully retrieve all data", HttpStatus.FOUND);
    }

    @GetMapping(PathApi.ID)
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        OrderResponse orderResponse = orderService.getOrderById(id);
        return responseBuilder.buildResponse(orderResponse, "Successfully retrieve all data", HttpStatus.FOUND);
    }

    @GetMapping(PathApi.DETAIL_ID)
    public ResponseEntity<?> getOrderDetailById(@PathVariable String id) {
        OrderDetailResponse orderDetailResponse = orderDetailService.getOrderDetailById(id);
        return responseBuilder.buildResponse(orderDetailResponse, "Successfully retrieve data", HttpStatus.FOUND);
    }
}
