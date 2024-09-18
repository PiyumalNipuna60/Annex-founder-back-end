package com.example.annex_founder.repo;

import com.example.annex_founder.model.Admins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepo extends JpaRepository<Admins, Long> {
    List<Admins> findByUserName(String username);
}
