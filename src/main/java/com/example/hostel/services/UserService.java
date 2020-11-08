package com.example.hostel.services;

import com.example.hostel.domain.Role;
import com.example.hostel.domain.User;
import com.example.hostel.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService  implements UserDetailsService {
    public final PasswordEncoder passwordEncoder;
    public final UserRepository userRepository;
    public final MailSenderService mailSenderService;

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
                            "Спасибо что выбрали BestPlace. " +
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
}
