package com.example.hostel.services;

import com.example.hostel.domain.Room;
import com.example.hostel.domain.Status;
import com.example.hostel.domain.User;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    public final RoomRepository roomRepository;
    public final DateRoomRepository dateRoomRepository;

    public void saveRoom (Room room){
        roomRepository.save(room);
    }

    public List<Room> findAllRoom() {
        return roomRepository.findAll();
    }



//    public void deleteRoom(@PathVariable(value = "id") long id){
//        Room room = roomRepository.findById(id);
//        roomRepository.delete(room);
//    }
//    public List<Room> findAllRooms() {
//    Iterable<Room> rooms = roomRepository.findAll();
//    List <Room> roomList = new ArrayList<>();
//    rooms.forEach(roomList::add);
//    return roomList;
//    }
}
