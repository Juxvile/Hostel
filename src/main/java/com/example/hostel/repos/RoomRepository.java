package com.example.hostel.repos;

import com.example.hostel.domain.Room;
import com.example.hostel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
