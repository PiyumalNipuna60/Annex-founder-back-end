package com.example.annex_founder.repo;

import com.example.annex_founder.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    List<Customer> findByUserName(String userName);

    Customer findByEmail(String email);

}
