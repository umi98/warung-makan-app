package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.PathApi;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderService;
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
//    private final OrderRequest orderRequest;

    @PostMapping
    public ResponseEntity<CommonResponse<OrderResponse>> addNewOrder (@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.addNewTransaction(orderRequest);
        CommonResponse<OrderResponse> response = CommonResponse.<OrderResponse>builder()
                .message("Successfully add new order")
                .statusCode(HttpStatus.CREATED.value())
                .data(orderResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderResponse> orderResponse = orderService.getAllOrders();
        CommonResponse<List<OrderResponse>> response = CommonResponse.<List<OrderResponse>>builder()
                .message("Successfully retrieve all data")
                .statusCode(HttpStatus.FOUND.value())
                .data(orderResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

//    @GetMapping(PathApi.ID)
//    public ResponseEntity<?> getOrderById(@PathVariable String id) {
//        OrderResponse orderResponse = orderService.getOrderById(id);
//        CommonResponse<OrderResponse> response = CommonResponse.<OrderResponse>builder()
//                .message("Successfully retrieve data")
//                .statusCode(HttpStatus.FOUND.value())
//                .data(orderResponse)
//                .build();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }

    @GetMapping(PathApi.ID)
    public ResponseEntity<?> getOrderDetailById(@PathVariable String id) {
        OrderDetailResponse orderDetailResponse = orderDetailService.getOrderDetailById(id);
        CommonResponse<OrderDetailResponse> response = CommonResponse.<OrderDetailResponse>builder()
                .message("Successfully retrieve data")
                .statusCode(HttpStatus.FOUND.value())
                .data(orderDetailResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
