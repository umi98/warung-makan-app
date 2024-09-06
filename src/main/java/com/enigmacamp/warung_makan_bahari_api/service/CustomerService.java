package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer newCustomer);
    Customer getCustomerById(String id);
    List<Customer> getAllCustomers();
    Customer editCustomer(Customer editCustomer);
    void deleteCustomer(String id);

}
