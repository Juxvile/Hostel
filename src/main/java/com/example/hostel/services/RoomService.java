package com.example.hostel.services;

import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Reviews;
import com.example.hostel.domain.Room;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.ReviewsRepository;
import com.example.hostel.repos.RoomRepository;
import com.example.hostel.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RoomService {
    public final RoomRepository roomRepository;
    public final DateRoomRepository dateRoomRepository;
    public final ReviewsRepository reviewsRepository;
    public final UserRepository userRepository;

    public void saveRoom (Room room){
        roomRepository.save(room);
    }

    @Cacheable("allRoomsCache")
    public List<Room> findAllRoom() {
        try{
            TimeUnit.SECONDS.sleep(3);
            return roomRepository.findAll();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteRoom(@PathVariable(value = "id") long id){
        List<Reviews> reviewsList = reviewsRepository.findRoomById(id);
        for (Reviews reviews : reviewsList){
            reviewsRepository.delete(reviews);
        }
        List<DateRoom> dateRoomsList = dateRoomRepository.findRoomById(id);
        for (DateRoom dateRoom : dateRoomsList){
            dateRoomRepository.delete(dateRoom);
        }
        Room room = roomRepository.findById(id);
        roomRepository.delete(room);
    }
}
