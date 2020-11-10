package com.example.hostel.services;

import com.example.hostel.domain.Reviews;
import com.example.hostel.domain.Room;
import com.example.hostel.repos.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewsService {

    public final ReviewsRepository reviewsRepository;

    public void saveReview (Reviews reviews){
        reviewsRepository.save(reviews);
    }
}
