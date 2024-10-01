package com.example.annex_founder.service.impl;

import com.example.annex_founder.dto.CustomerDto;
import com.example.annex_founder.model.Customer;
import com.example.annex_founder.repo.CustomerRepo;
import com.example.annex_founder.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo, ModelMapper modelMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean isExitCustomer(CustomerDto customerDto) {
        Customer customer = customerRepo.findByEmail(customerDto.getEmail());
        return customer != null;
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = dtoToEntity(customerDto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer save = customerRepo.save(customer);
        return entityToCustomerDto(save);
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> all = customerRepo.findAll();
        ArrayList<CustomerDto> allCustomers = new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(entityToCustomerDto(customer));
        }
        return allCustomers;
    }

    @Override
    public CustomerDto searchCustomer(Long customerId) {
        Optional<Customer> byId = customerRepo.findById(customerId);
        return byId.map(this::entityToCustomerDto).orElse(null);
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
        Optional<Customer> byId = customerRepo.findById(customerId);
        if (byId.isPresent()) {
            Customer customer = dtoToEntity(customerDto);
            customer.setCustomerId(customerId);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            Customer save = customerRepo.save(customer);
            return entityToCustomerDto(save);
        } else {
            return null;
        }
    }

    @Override
    public CustomerDto deleteCustomer(Long customerId) {
        Optional<Customer> byId = customerRepo.findById(customerId);
        if (byId.isPresent()) {
            customerRepo.deleteById(customerId);
            return entityToCustomerDto(byId.get());
        } else {
            return null;
        }
    }

    @Override
    public CustomerDto customerLogin(CustomerDto dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<Customer> userNames = customerRepo.findByUserName(dto.getUserName());
        for (Customer name : userNames) {
            boolean isPasswordMatches = passwordEncoder.matches(dto.getPassword(), name.getPassword());
            if (isPasswordMatches) {
                return entityToCustomerDto(name);
            }
        }
        return null;
    }

    private Customer dtoToEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }

    private CustomerDto entityToCustomerDto(Customer save) {
        return modelMapper.map(save, CustomerDto.class);
    }
}

