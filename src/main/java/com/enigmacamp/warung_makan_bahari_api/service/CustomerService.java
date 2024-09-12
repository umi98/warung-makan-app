package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer addCustomer(Customer newCustomer);
    Customer getCustomerById(String id);
    Page<Customer> getAllCustomers(PagingRequest request);
    Customer editCustomer(Customer editCustomer);
    void deleteCustomer(String id);

}
