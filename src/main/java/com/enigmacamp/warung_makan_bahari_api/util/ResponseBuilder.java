package com.enigmacamp.warung_makan_bahari_api.util;

import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {
    public <T> ResponseEntity<CommonResponse<T>> buildResponse(T data, String message, HttpStatus status) {
        CommonResponse<T> response = CommonResponse.<T>builder()
                .message(message)
                .statusCode(status.value())
                .data(data)
                .build();
        return ResponseEntity
                .status(status)
                .body(response);
    }
}
