package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.util.ResponseBuilderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)

//@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
//    @InjectMocks
//    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;
    @MockBean
    private ResponseBuilderUtil responseBuilderUtil;

//    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService, responseBuilderUtil)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllCustomer() {
    }

    @Test
    void getCustomerById() {
    }

    @WithMockUser
    @Test
    void addCustomer_ShouldReturnNewCustomer() throws Exception {
        // Arrange
        Customer customer = Customer.builder()
                .fullName("bawang")
                .phoneNumber("087")
                .isMember(true)
                .build();
        CustomerResponse response = CustomerResponse.builder()
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .isMember(customer.getIsMember()).build();
        when(customerService.addCustomer(customer)).thenReturn(response);
        // Act
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
//                .andDo(result -> result.getResponse().getContentAsString());
//                .andDo(result -> System.out.println(result.getResponse() + "hasil"));
                .andExpect(jsonPath("$.data.fullName").value("bawang"))
                .andExpect(jsonPath("$.data.phoneNumber").value("087"))
                .andExpect(jsonPath("$.data.isMember").value(true));
//                .andReturn();
//        String jsonResponse = result.getResponse().getContentAsString();
//        System.out.println("Response JSON: " + jsonResponse);
//        verify(customerService,times(1)).addCustomer(customer);
    }

    @Test
    void editCustomer() {
    }

    @Test
    void deleteCustomerById() {
    }
}