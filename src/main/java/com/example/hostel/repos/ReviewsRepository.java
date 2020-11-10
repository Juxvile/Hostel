package com.example.hostel.repos;

import com.example.hostel.domain.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository <Reviews, Long> {
}
