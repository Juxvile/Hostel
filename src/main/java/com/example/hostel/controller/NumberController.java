package com.example.hostel.controller;

import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Reviews;
import com.example.hostel.domain.Room;
import com.example.hostel.domain.User;
import com.example.hostel.repos.RoomRepository;
import com.example.hostel.services.DateRoomService;
import com.example.hostel.services.ReviewsService;
import com.example.hostel.services.RoomService;
import com.example.hostel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/numbers")
@RequiredArgsConstructor
public class NumberController {

    public final RoomRepository roomRepository;
    public final RoomService roomService;
    public final DateRoomService dateRoomService;
    public final ReviewsService reviewsService;
    public final UserService userService;

    @GetMapping("/add")
    public String addRoom(){
        return "addroom";
    }

    @PostMapping("/add")
    public String addRoom(
            @Valid Room room,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()){
            model.addAttribute("room",room);
            return "addroom";
        } else {
            roomService.saveRoom(room);
            return "redirect:/numbers";
        }
    }

    @GetMapping
    public String numbers(Model model) {
        model.addAttribute("numbers", roomService.findAllRoom());
        return "numbers";
    }

    @GetMapping("{id}")
    public String rooms(
            @PathVariable("id") Room room,
            @AuthenticationPrincipal User user,
            Model model
    ) {
//        List<User> users = userService.users();
        model.addAttribute("reviews", reviewsService.findByRoom(room));
        model.addAttribute("user", user);
        model.addAttribute("room", room);
        return "room";
    }

    @PostMapping
    public String roomReview (@Valid Reviews reviews,
                              Model model){
        model.addAttribute("reviews",reviews);
        reviewsService.saveReview(reviews);
        return "redirect:/numbers/"+reviews.getRoom().getId();
    }

    @PostMapping("{id}")
    public String roomReserve (@Valid DateRoom dateRoom,
                            @AuthenticationPrincipal User user,
                            Model model){
        model.addAttribute("user", user);
        dateRoomService.reserveRoom(dateRoom);
        return "redirect:/";
    }

}