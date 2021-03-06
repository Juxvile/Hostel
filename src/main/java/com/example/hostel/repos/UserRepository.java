package com.example.hostel.repos;

import com.example.hostel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);
    User findByActivationCode(String code);
    User findByEmail(String email);
    User findById (long id);

    User findByPhoneNumber(String phoneNumber);

}
