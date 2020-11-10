package com.example.hostel.controller;

import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.Room;
import com.example.hostel.repos.RoomRepository;
import com.example.hostel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/numbers")
@RequiredArgsConstructor
public class NumberController {

    public final RoomRepository roomRepository;
    public final RoomService roomService;

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
            Model model
    ) {
        model.addAttribute("number", room);
        return "room";
    }

    @PostMapping("{id}")
    public String roomReserve (@Valid DateRoom dateRoom,
                            Model model){
//        roomService.reserveRoom(room);
        return "redirect:/";
    }

}