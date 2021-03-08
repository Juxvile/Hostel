package com.example.hostel.controller;

import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Reviews;
import com.example.hostel.domain.Room;
import com.example.hostel.domain.User;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.RoomRepository;
import com.example.hostel.services.DateRoomService;
import com.example.hostel.services.ReviewsService;
import com.example.hostel.services.RoomService;
import com.example.hostel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(value = "/numbers")
@RequiredArgsConstructor
public class RoomController {

    public final RoomRepository roomRepository;
    public final RoomService roomService;
    public final DateRoomService dateRoomService;
    public final DateRoomRepository dateRoomRepository;
    public final ReviewsService reviewsService;
    public final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/add")
    public String addRoom()  {
        return "addroom";
    }


    @PostMapping("/add")
    public String addRoom(
            @Valid Room room,
            @RequestParam("file") MultipartFile[] files,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("room", room);
            return "addroom";
        } else {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            int count = 0;
            for (MultipartFile file : files) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                switch (count) {
                    case (0):
                        room.setFilename(resultFilename);
                        break;
                    case (1):
                        room.setFilename2(resultFilename);
                        break;
                    case (2):
                        room.setFilename3(resultFilename);
                        break;
                    case (3):
                        room.setFilename4(resultFilename);
                        break;
                    case (4):
                        room.setFilename5(resultFilename);
                        break;
                }
                count++;
                roomService.saveRoom(room);
            }
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
            DateRoom dateRoom,
            @PathVariable("id") Room room,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("dateRoom", dateRoom);
        model.addAttribute("reviews", reviewsService.findByRoom(room));
        model.addAttribute("user", user);
        model.addAttribute("room", room);
        return "room";
    }

    @PostMapping
    public String roomReview(@Valid Reviews reviews,
                             Model model) {
        model.addAttribute("reviews", reviews);
        reviewsService.saveReview(reviews);
        return "redirect:/numbers/" + reviews.getRoom().getId();
    }

    @PostMapping("{id}")
    public String roomReserve(
            @Valid DateRoom dateRoom,
            BindingResult bindingResult,
            Model model,
            @PathVariable("id") Room room,
            @AuthenticationPrincipal User user
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("dateRoom", dateRoom);
            model.addAttribute("user", user);
            model.addAttribute("reviews", reviewsService.findByRoom(room));
            model.addAttribute("room", room);
            model.addAttribute("message", "");
            return "room";
        } else {
            if (dateRoom.getEntryDate().isAfter(dateRoom.getLeaveDate())) {
                model.addAttribute("dateRoom", dateRoom);
                model.addAttribute("user", user);
                model.addAttribute("reviews", reviewsService.findByRoom(room));
                model.addAttribute("room", room);
                model.addAttribute("message", "Введите корректную дату отъезда");
                return "room";
            } else if (!dateRoomService.reserveRoom(dateRoom, user, room)) {
                model.addAttribute("dateRoom", dateRoom);
                model.addAttribute("user", user);
                model.addAttribute("reviews", reviewsService.findByRoom(room));
                model.addAttribute("room", room);
                model.addAttribute("message", "На данную дату номер уже забронирован");
                return "room";
            } else {
                return "redirect:/";
            }
        }
    }
}