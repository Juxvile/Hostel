package com.example.hostel.services;


import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Room;
import com.example.hostel.repos.DateRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DateRoomService {
    public final DateRoomRepository dateRoomRepository;

    public void reserveRoom(DateRoom dateRoom){
        dateRoomRepository.save(dateRoom);
    }
    public List<DateRoom> findAllDateRoom() {
        return dateRoomRepository.findAll();
    }

}

