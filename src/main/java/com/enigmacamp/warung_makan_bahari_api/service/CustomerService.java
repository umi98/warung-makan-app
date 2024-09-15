package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse addCustomer(Customer newCustomer);
    CustomerResponse getCustomerById(String id);
    Customer getCustById(String id);
    Page<CustomerResponse> getAllCustomers(PagingRequest request);
    CustomerResponse editCustomer(String id, CustomerRequest editCustomer);
    void deleteCustomer(String id);

}
