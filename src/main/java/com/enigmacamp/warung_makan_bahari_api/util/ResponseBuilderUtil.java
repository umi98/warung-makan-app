package com.enigmacamp.warung_makan_bahari_api.util;

import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseBuilderUtil {
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

    public <T> ResponseEntity<CommonResponse<List<T>>> buildResponsePaging(PagingResponse paging, Page<T> data, String message, HttpStatus status) {
        CommonResponse<List<T>> response = CommonResponse.<List<T>>builder()
                .message(message)
                .statusCode(status.value())
                .data(data.getContent())
                .paging(paging)
                .build();
        return ResponseEntity
                .status(status)
                .body(response);
    }
}
