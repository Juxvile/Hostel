package com.example.hostel.controller;

import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.User;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.UserRepository;
import com.example.hostel.services.DateRoomService;
import com.example.hostel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UserController {

    public final UserRepository userRepository;
    public final UserService userService;
    public final DateRoomService dateRoomService;
    public final DateRoomRepository dateRoomRepository;

    @GetMapping("/profile")
    public String profile (
            @AuthenticationPrincipal User user,
            Model model){
        model.addAttribute("user",user);
        return "profile";
    }


    @GetMapping("/login")
    public String login(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "login";
    }

    @PostMapping("/login")
    public String logUser(@RequestParam String username,
                          @RequestParam String password,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "login";

        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/listofusers")
    public String listOfUsers(Model model){
        List <User> users = userService.users();
        List <DateRoom> dateRooms = dateRoomService.dateRooms();
        model.addAttribute("users", users);
        model.addAttribute("dateRooms", dateRooms);
        return "listofusers";
    }

    @PostMapping("/listofusers/{id}/remove")
    public String deleteUser(@PathVariable (value = "id") long id,
                             Model model
    ){
        List <User> users = userService.users();
        model.addAttribute("users", users);
        User user1 = userRepository.findById(id);
        userRepository.delete(user1);

        return "listofusers";
    }
}
