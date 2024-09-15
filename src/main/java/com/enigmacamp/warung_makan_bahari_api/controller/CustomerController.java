package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<?> getAllCustomer(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<CustomerResponse> customers = customerService.getAllCustomers(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(customers.getTotalElements())
                .totalPage(customers.getTotalPages())
                .build();
        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .message("Successfully retrieve all data")
                .statusCode(HttpStatus.OK.value())
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("Successfully retrieve data")
                .statusCode(HttpStatus.OK.value())
                .data(customerService.getCustomerById(id))
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                    .message("Successfully created customer")
                    .statusCode(HttpStatus.OK.value())
                    .data(customerService.addCustomer(customer))
                    .build();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (RuntimeException e) {
            throw new RuntimeException("Phone number already exists");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCustomerv2(@PathVariable String id, @RequestBody CustomerRequest customer) {
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("Successfully edit data")
                .statusCode(HttpStatus.OK.value())
                .data(customerService.editCustomer(id, customer))
                .build();
        return ResponseEntity
               .status(HttpStatus.OK)
               .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomer(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("Data deleted")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
