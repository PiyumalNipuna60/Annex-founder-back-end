package com.example.annex_founder.service;

import com.example.annex_founder.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    boolean isExitCustomer(CustomerDto customerDto);

    CustomerDto saveCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomer();

    CustomerDto searchCustomer(Long customerId);

    CustomerDto updateCustomer(Long customerId, CustomerDto customerDto);

    CustomerDto deleteCustomer(Long customerId);

    CustomerDto customerLogin(CustomerDto dto);
}
