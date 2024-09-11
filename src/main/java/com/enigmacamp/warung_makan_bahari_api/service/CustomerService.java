package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerPagingRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer newCustomer);
    Customer getCustomerById(String id);
    Page<Customer> getAllCustomers(CustomerPagingRequest request);
    Customer editCustomer(Customer editCustomer);
    void deleteCustomer(String id);

}
