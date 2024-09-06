package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer getCustomerById(String id) {
        return findByIdOrThrowException(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer editCustomer(Customer editCustomer) {
        findByIdOrThrowException(editCustomer.getId());
        return customerRepository.save(editCustomer);
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customer = findByIdOrThrowException(id);
        customerRepository.delete(customer);
    }

    public Customer findByIdOrThrowException(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new RuntimeException("ID not found"));
    }

}
