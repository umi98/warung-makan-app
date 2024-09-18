package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
class CustomerServiceImplTest {
    @InjectMocks // Use class as mock
    private CustomerServiceImpl customerServiceImpl;
    @Mock // use mocking data
    private CustomerRepository customerRepository;
    @Mock
    private ValidationUtil validationUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCustomer_WhenInputValid_ShouldReturnCustomerResponse() {
        // A3 (Arrange, Act, Assert)
        // Arrange
        // prepare customer data
        Customer customer = Customer.builder()
                .fullName("ayam")
                .phoneNumber("0987654321")
                .isMember(true)
                .build();

        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        CustomerResponse custresponse = customerServiceImpl.addCustomer(customer);

        // Assert
        assertNotNull(custresponse);
        assertEquals(customer.getFullName(), custresponse.getFullName());
        assertEquals(customer.getPhoneNumber(), custresponse.getPhoneNumber());
        assertEquals(customer.getIsMember(), custresponse.getIsMember());
    }

    @Test
    void addCustomer_WhenSamePhoneNumberInputted_ShouldReturnThrowable() {
        //Arrange
        Customer customer = Customer.builder()
                .fullName("ayam")
                .phoneNumber("0987654321")
                .isMember(true)
                .build();
        when(customerRepository.save(customer)).thenThrow(new DataIntegrityViolationException("Phone number has been used by another account"));
        //Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> customerServiceImpl.addCustomer(customer));
        assertEquals("409 CONFLICT \"Phone number has been used by another account\"", exception.getMessage());
    }

    @Test
    void getCustomerById_WhenCustomerFound_ShouldReturnCustomerResponse() {
        Customer customer = Customer.builder()
                .id("id01")
                .fullName("ayam")
                .phoneNumber("0987654321")
                .isMember(true)
                .build();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        CustomerResponse response = customerServiceImpl.getCustomerById(customer.getId());
        assertNotNull(response);
        assertEquals(customer.getFullName(), response.getFullName());
        assertEquals(customer.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(customer.getIsMember(), response.getIsMember());
    }

    @Test
    void getCustomerById_WhenCustomerNotFound_ShouldReturnNotFound() {
        when(customerRepository.findById("id01")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> customerServiceImpl.getCustomerById("id01"));
        assertEquals("404 NOT_FOUND \"ID not found\"", exception.getMessage());
        verify(customerRepository, times(1)).findById("id01");
    }

    @Test
    void getCustById_WhenCustomerFound_ShouldReturnCustomer() {
        Customer customer = Customer.builder()
                .id("id01")
                .fullName("ayam")
                .phoneNumber("0987654321")
                .isMember(true)
                .build();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Customer result = customerServiceImpl.getCustById(customer.getId());
        assertNotNull(result);
        assertEquals(customer.getFullName(), result.getFullName());
        assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(customer.getIsMember(), result.getIsMember());
    }

    @Test
    void getCustById_WhenCustomerNotFound_ShouldReturnNotFound() {
        when(customerRepository.findById("id01")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> customerServiceImpl.getCustById("id01"));
        assertEquals("404 NOT_FOUND \"ID not found\"", exception.getMessage());
        verify(customerRepository, times(1)).findById("id01");
    }

    @Test
    void getAllCustomers_WhenExecuted_ShouldReturnPagedCustomer() {
        Customer customer = new Customer();
        PagingRequest request = new PagingRequest();
        request.setPage(1);
        request.setSize(10);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Customer> pageResult = new PageImpl<>(Collections.singletonList(customer),pageable, 1);
        when(customerRepository.findAll(pageable)).thenReturn(pageResult);
        Page<CustomerResponse> result = customerServiceImpl.getAllCustomers(request);
        assertEquals(1, result.getTotalElements());
        verify(customerRepository, times(1)).findAll(pageable);
    }

    @Test
    void editCustomer_WhenInputValid_ShouldReturnCustomerResponse() {

    }

    @Test
    void deleteCustomer_WhenCustomerFound_ShouldDeleteCustomer() {
        Customer customer = Customer.builder()
                .id("id01")
                .fullName("ayam")
                .phoneNumber("0987654321")
                .isMember(true)
                .build();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        customerServiceImpl.deleteCustomer(customer.getId());
//        assertNotNull(result);
//        assertEquals(customer.getFullName(), result.getFullName());
//        assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());
//        assertEquals(customer.getIsMember(), result.getIsMember());
    }


}