package com.example.hostel.repos;

import com.example.hostel.domain.Room;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findById (long id);
}
