package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.PathApi;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.CUSTOMERS)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final ResponseBuilder responseBuilder;

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
        return responseBuilder.buildResponse(pagingResponse, "Successfully retrieve all data", HttpStatus.OK);
    }

    @GetMapping(PathApi.ID)
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return responseBuilder.buildResponse(response, "Successfully retrieve all data", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            CustomerResponse response = customerService.addCustomer(customer);
            return responseBuilder.buildResponse(response, "Successfully created customer", HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException("Phone number already exists");
        }
    }

    @PutMapping(PathApi.ID)
    public ResponseEntity<?> editCustomerv2(@PathVariable String id, @RequestBody CustomerRequest customer) {
        CustomerResponse response = customerService.editCustomer(id, customer);
        return responseBuilder.buildResponse(response, "Successfully edit customer", HttpStatus.OK);
    }

    @DeleteMapping(PathApi.ID)
    public ResponseEntity<?> deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return responseBuilder.buildResponse("OK", "Data deleted", HttpStatus.OK);
    }
}
