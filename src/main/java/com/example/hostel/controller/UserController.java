package com.example.hostel.controller;

import com.example.hostel.domain.DateRoom;
import com.example.hostel.domain.User;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.UserRepository;
import com.example.hostel.services.DateRoomService;
import com.example.hostel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
    public String deleteUser(@PathVariable(value = "id") long id){
            userService.deleteUser(id);
            return "redirect:/listofusers";
    }

    @GetMapping("/profile/changeusername")
    public String changeUsername(@AuthenticationPrincipal User user,
                                 Model model){
        model.addAttribute("user", user);
        return "changeusername";
    }
    @GetMapping("/profile/changephonenumber")
    public String changePhoneNumber(@AuthenticationPrincipal User user,
                                 Model model){
        model.addAttribute("user", user);
        return "changephonenumber";
    }
    @GetMapping("/profile/changeemail")
    public String changeEmail(@AuthenticationPrincipal User user,
                                 Model model){
        model.addAttribute("user", user);
        return "changeemail";
    }


    @PostMapping("/profile/changeusername")
    public String changeName(@RequestParam(name = "username",defaultValue = "") String username,
                             @RequestParam(name = "usernameAgain",defaultValue = "") String usernameAgain,
                             @AuthenticationPrincipal User user,
                             Model model){
        Map <String, String> usernameErrors = userService.changeUsername(username, usernameAgain, user);
        if (usernameErrors.isEmpty()){
            return "redirect:/profile";
        } else {
            model.mergeAttributes(usernameErrors);
            model.addAttribute("username", username);
            model.addAttribute("message", usernameErrors.values());
            model.addAttribute("usernameAgain", usernameAgain);
            return "changeusername";
        }
    }

    @PostMapping("/profile/changephonenumber")
    public String changePhoneNumber(@RequestParam(name = "phoneNumber",defaultValue = "") String phoneNumber,
                             @RequestParam(name = "phoneNumberAgain",defaultValue = "") String phoneNumberAgain,
                             @AuthenticationPrincipal User user,
                             Model model){
        Map <String, String> phoneNumberErrors = userService.changePhoneNumber(phoneNumber, phoneNumberAgain, user);
        if (phoneNumberErrors.isEmpty()){
            return "redirect:/profile";
        } else {
            model.mergeAttributes(phoneNumberErrors);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("message", phoneNumberErrors.values());
            model.addAttribute("phoneNumberAgain", phoneNumberAgain);
            return "changephonenumber";
        }
    }

    @PostMapping("/profile/changeemail")
    public String changeEmail(@RequestParam(name = "email",defaultValue = "") String email,
                                    @RequestParam(name = "emailAgain",defaultValue = "") String emailAgain,
                                    @AuthenticationPrincipal User user,
                                    Model model){
        Map <String, String> emailErrors = userService.changeEmail(email, emailAgain, user);
        if (emailErrors.isEmpty()){
            return "redirect:/profile";
        } else {
            model.mergeAttributes(emailErrors);
            model.addAttribute("email", email);
            model.addAttribute("message", emailErrors.values());
            model.addAttribute("emailAgain", emailAgain);
            return "changeemail";
        }
    }

//    @GetMapping("/profile/avatar")
//    public String avatar (@AuthenticationPrincipal User user,
//                          Model model){
//        model.addAttribute("user", user);
//        return "avatar";
//    }
//
//    @GetMapping("/profile/avatar")
//    public String addAvatar (@RequestParam(name = "avatar",defaultValue = "") String avatar,
//                             @AuthenticationPrincipal User user,
//                             Model model){
//        model.addAttribute("user", user);
//        return "avatar";
//    }
}
