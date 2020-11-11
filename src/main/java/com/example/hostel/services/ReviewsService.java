package com.example.hostel.services;

import com.example.hostel.domain.Reviews;
import com.example.hostel.domain.Room;
import com.example.hostel.domain.User;
import com.example.hostel.repos.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewsService {

    public final ReviewsRepository reviewsRepository;

    public List<Reviews> reviews(){
        return reviewsRepository.findAll();
    }

    public void saveReview (Reviews reviews){
        reviewsRepository.save(reviews);
    }

    public List <Reviews> findByRoom(Room room) {
        return reviewsRepository.findByRoomId(room.getId());
    }
}
