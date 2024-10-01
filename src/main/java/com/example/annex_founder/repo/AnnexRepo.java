package com.example.annex_founder.repo;

import com.example.annex_founder.model.Annex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnexRepo extends JpaRepository<Annex, Long> {
}
