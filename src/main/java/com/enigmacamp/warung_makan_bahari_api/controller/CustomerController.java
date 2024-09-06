package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/v1/customers")
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/api/v1/customers/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/api/v1/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

// Using Path variable to edit customer
//    @PutMapping("/api/v1/customers/{id}")
//    public Customer editCustomerv1(@PathVariable String id, @RequestBody Customer customer) {
//        Optional<Customer> findData = customerRepository.findById(id);
//        if (findData.isEmpty()) {
//            throw new RuntimeException("Id not found");
//        }
//        findData.get().setFullName(customer.getFullName());
//        findData.get().setPhoneNumber(customer.getPhoneNumber());
//        findData.get().setIsMember(customer.getIsMember());
//        return customerRepository.save(findData.get());
//    }

// Edit customer and get id directly from request body
    @PutMapping("/api/v1/customers")
    public Customer editCustomerv2(@RequestBody Customer customer) {
       return customerService.editCustomer(customer);
    }

    @DeleteMapping("api/v1/customers/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }
}
