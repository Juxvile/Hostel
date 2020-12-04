package com.example.hostel.controller;

import com.example.hostel.domain.User;
import com.example.hostel.repos.UserRepository;
import com.example.hostel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    public final UserRepository userRepository;
    public final UserService userService;

    @GetMapping("/registration")
    public String registration(User user,
                               Model model
    ){
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ){
        if (bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "registration";
        } else {
            userService.addUser(user);
            return "redirect:/mailsend";
        }
    }

    @GetMapping("/mailsend")
    public String mailSendPage(
            Model model
    ) {
        model.addAttribute("message", "Вам на почту выслано письмо, перейдите по ссылке в письме для продолжения регистрации");
        return "mailsend";
    }

    @GetMapping("/activate/{code}")
    public String activate(
            Model model,
            @PathVariable String code
    ){
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "Вы успешно активировали пользователя!");
        } else {
            model.addAttribute("message", "Код активации не найден");
        }
        return "activation";
    }
}
