package com.example.hostel.services;


import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Room;
import com.example.hostel.domain.User;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DateRoomService {
    public final DateRoomRepository dateRoomRepository;
    public final RoomRepository roomRepository;

    public List<DateRoom> dateRooms(){
        return dateRoomRepository.findAll();
    }

    public boolean reserveRoom(DateRoom dateRoom, User user, Room room){
        dateRoom.setId(null); // костыль, но работает
        dateRoom.setUser(user);
        dateRoom.setRoom(room);
        boolean datesEmpty = dateRoomRepository.findAllBetweenDates(dateRoom.getEntryDate(), dateRoom.getLeaveDate()).isEmpty();
        if (datesEmpty) {
            dateRoomRepository.save(dateRoom);
            return true;
        } else {
            return false;
        }
    }
}

