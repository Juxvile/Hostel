package com.example.hostel.services;


import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Reviews;
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

    public List<DateRoom> findByRoomId (Long id){
        return dateRoomRepository.findByRoomId(id);
    }




    public boolean reserveRoom(DateRoom dateRoom, User user,Room room){
        dateRoom.setId(null); // костыль, но работает
        dateRoom.setUser(user);
        dateRoom.setRoom(room);
        List<DateRoom> dateRoomList = dateRoomRepository.findByRoomId(room.getId());
        boolean datesEmpty = dateRoomRepository.findAllBetweenDates(dateRoom.getEntryDate(), dateRoom.getLeaveDate()).isEmpty();
        for (DateRoom dateRoom1 : dateRoomList){
            if (datesEmpty) {
                dateRoomRepository.save(dateRoom1);
                datesEmpty = true;
            } else {
                datesEmpty = false;
            }
        }
        return datesEmpty;
    }
}

