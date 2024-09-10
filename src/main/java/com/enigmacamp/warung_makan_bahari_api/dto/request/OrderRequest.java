package com.enigmacamp.warung_makan_bahari_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String customerId;
    private String tableId;
    private List<OrderDetailRequest> orderDetails;
}
