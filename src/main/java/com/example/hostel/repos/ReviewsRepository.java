package com.example.hostel.repos;

import com.example.hostel.domain.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository <Reviews, Long> {

    List<Reviews> findByRoomId(Long id);
    List<Reviews> findUserById (Long id);

    List<Reviews> findRoomById(long id);
}
