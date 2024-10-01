package com.example.annex_founder.repo;

import com.example.annex_founder.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
