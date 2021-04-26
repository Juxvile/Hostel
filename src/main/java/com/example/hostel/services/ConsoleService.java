package com.example.hostel.services;

import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Reviews;
import com.example.hostel.domain.Room;
import com.example.hostel.domain.User;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.ReviewsRepository;
import com.example.hostel.repos.RoomRepository;
import com.example.hostel.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsoleService {
    public final DateRoomRepository dateRoomRepository;
    public final ReviewsRepository reviewsRepository;
    public final RoomRepository roomRepository;
    public final UserRepository userRepository;

    @Scheduled(fixedDelay = 60000L)
    public void statistics () throws NoSuchFieldException {
        int countOfReservation = 0;
        int countOfRooms = 0;
        int countOfUsers = 0;
        int countOfReviews = 0;
        for (DateRoom dateRoom : dateRoomRepository.findAll()){
            countOfReservation++;
        }
        for (Room room : roomRepository.findAll()){
            countOfRooms++;
        }
        for (User user : userRepository.findAll()){
            countOfUsers++;
        }
        for (Reviews reviews : reviewsRepository.findAll()){
            countOfReviews++;
        }
        System.out.println("На данный момент: \n" +
                "Число броней - " + countOfReservation + "\n" +
                "Число комнат - " + countOfRooms + "\n" +
                "Число гостей - " + countOfUsers + "\n" +
                "Число отзывов - " + countOfReviews);
    }
}
