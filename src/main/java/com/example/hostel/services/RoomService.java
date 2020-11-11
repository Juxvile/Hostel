package com.example.hostel.services;

import com.example.hostel.domain.Room;
import com.example.hostel.repos.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    public final RoomRepository roomRepository;

    public void saveRoom (Room room){
        roomRepository.save(room);
    }

    public List<Room> findAllRoom() {
        return roomRepository.findAll();
    }
}
