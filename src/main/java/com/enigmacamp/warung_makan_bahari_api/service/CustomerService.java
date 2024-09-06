package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;

import java.util.List;

public interface CustomerService {
//    private Customer;
    public Customer addCustomer(Customer newCustomer);
    public Customer getCustomerById(String id);
    public List<Customer> getAllCustomers();
    public Customer editCustomer(Customer editCustomer);
    public void deleteCustomer(String id);

}
