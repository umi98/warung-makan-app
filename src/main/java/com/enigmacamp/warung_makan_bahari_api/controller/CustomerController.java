package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping()
    public Customer addCustomer(@Valid @RequestBody Customer customer) {
        try {
            return customerService.addCustomer(customer);
        } catch (RuntimeException e) {
            throw new RuntimeException("Phone number already exists");
        }
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
    @PutMapping()
    public Customer editCustomerv2(@Valid @RequestBody Customer customer) {
       return customerService.editCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }
}
