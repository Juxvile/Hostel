package com.example.hostel.services;


import com.example.hostel.domain.DateRoom;
import com.example.hostel.repos.DateRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DateRoomService {
    public final DateRoomRepository dateRoomRepository;

    public void reserveRoom(DateRoom dateRoom){
        dateRoomRepository.save(dateRoom);
    }

}

