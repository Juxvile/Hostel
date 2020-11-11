package com.example.hostel.repos;

import com.example.hostel.domain.Reviews;
import com.example.hostel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository <Reviews, Long> {

    List<Reviews> findByRoomId(Long id);
}
