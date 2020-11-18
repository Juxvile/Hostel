package com.example.hostel.validation;

import com.example.hostel.domain.User;
import com.example.hostel.repos.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {

    public final UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {

        if (validUsername(user)){
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("username").addConstraintViolation();
            return false;
        }
    }

    boolean validUsername(User user){
        if (user.getId() == null) {
            return userRepository.findByUsername(user.getUsername()) == null;
        } else {
            return user.getId() == userRepository.findByUsername(user.getUsername()).getId();
        }
    }
}