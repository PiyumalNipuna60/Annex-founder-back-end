package com.example.annex_founder.repo;

import com.example.annex_founder.model.HouseRenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRenterRepo extends JpaRepository<HouseRenter,Long> {
    HouseRenter findByEmail(String email);

    HouseRenter findByRenterId(Long renterId);
}
