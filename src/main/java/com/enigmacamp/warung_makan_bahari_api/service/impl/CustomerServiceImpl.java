package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse addCustomer(Customer newCustomer) {
        try {
            validationUtil.validate(newCustomer);
            customerRepository.save(newCustomer);
            return mapToResponse(newCustomer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number has been used by another account");
        }
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public Customer getCustById(String id) {
        return findByIdOrThrowException(id);
    }

    @Override
    public Customer getCustomerByUserId(UserCredential userCredential) {
        return customerRepository.findByUserCredential(userCredential);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(PagingRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(this::mapToResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse editCustomer(String id, CustomerRequest editCustomer) {
        try {
            validationUtil.validate(editCustomer);
            Customer currentCust = findByIdOrThrowException(id);
            currentCust.setFullName(editCustomer.getFullName());
            currentCust.setPhoneNumber(editCustomer.getPhoneNumber());
            customerRepository.save(currentCust);
            return mapToResponse(currentCust);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already used by another account");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCustomer(String id) {
        Customer customer = findByIdOrThrowException(id);
        customerRepository.delete(customer);
    }

    private Customer findByIdOrThrowException(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found"));
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .isMember(customer.getIsMember())
                .build();
    }

}
