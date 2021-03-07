package com.example.hostel.services;

import com.example.hostel.domain.Role;
import com.example.hostel.domain.User;
import com.example.hostel.repos.DateRoomRepository;
import com.example.hostel.repos.ReviewsRepository;
import com.example.hostel.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService  implements UserDetailsService {
    public final PasswordEncoder passwordEncoder;
    public final UserRepository userRepository;
    public final MailSenderService mailSenderService;
    public final DateRoomRepository dateRoomRepository;
    public final ReviewsRepository reviewsRepository;

    public void addUser(User user) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        sendMessage(user);

    }
    private void sendMessage(User user){
        if(!StringUtils.isEmpty(user.getEmail())){

            String message = String.format(
                    "Привет, %s! \n" +
                            "Спасибо что выбрали Royal Blood. " +
                            "Перейдите по ссылке чтобы активировать пользователя http://localhost:8082/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code){
        User userByCode = userRepository.findByActivationCode(code);

        if (userByCode == null){
            return false;
        } else {

            userByCode.setActivationCode(null);
            userByCode.setActive(true);
            userRepository.save(userByCode);

            return true;
        }
    }

    public List<User> users(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


    public void saveUser(User user, String username, Map<String, String> form){

        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }


    public void deleteUser(@PathVariable(value = "id") long id){
        User user = userRepository.findById(id);
        userRepository.delete(user);
    }


    public Map<String, String> changeUsername(String username, String usernameAgain, User user) {
        Map<String, String> errorMap = new HashMap<>();

        if(username.isEmpty()) {
            errorMap.put("message", "Имя не должно быть пустым");
        }
        if(!username.equals(usernameAgain)) {
            errorMap.put("message", "Имена не совпадают");
        }
        if(userRepository.findByUsername(username) != null) {
            errorMap.put("message", "Такое имя уже есть");
        }
        if(errorMap.isEmpty()) {
            user.setUsername(username);
            userRepository.save(user);
        }
        return errorMap;
    }


    public Map<String, String> changePhoneNumber(String phoneNumber, String phoneNumberAgain, User user) {
        Map<String, String> errorMap = new HashMap<>();

        if(phoneNumber.isEmpty()) {
            errorMap.put("message", "Телефон не должен быть пустым");
        }
        if(!phoneNumber.equals(phoneNumberAgain)) {
            errorMap.put("message", "Номера не совпадают");
        }
        if(userRepository.findByPhoneNumber(phoneNumber) != null) {
            errorMap.put("message", "Такое номер уже есть");
        }
        if(errorMap.isEmpty()) {
            user.setPhoneNumber(phoneNumber);
            userRepository.save(user);
        }
        return errorMap;
    }

    public Map<String, String> changeEmail(String email, String emailAgain, User user) {
        Map<String, String> errorMap = new HashMap<>();

        if(email.isEmpty()) {
            errorMap.put("message", "Email не должен быть пустым");
        }
        if(!email.equals(emailAgain)) {
            errorMap.put("message", "Email не совпадают");
        }
        if(userRepository.findByEmail(email) != null) {
            errorMap.put("message", "Такое email уже есть");
        }
        if(errorMap.isEmpty()) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
            user.setActive(false);
            userRepository.save(user);
            sendMessage(user);
            errorMap.put("message", "На вашу почту отправлено письмо с активацией");
        }
        return errorMap;
    }
}
