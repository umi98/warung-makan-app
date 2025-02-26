package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> addBulk(List<OrderDetail> orderDetails);
    OrderDetailResponse getOrderDetailById(String id);

}
