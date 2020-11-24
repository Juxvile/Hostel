package com.example.hostel.validation;


import com.example.hostel.domain.User;
import com.example.hostel.repos.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, User> {

    public final UserRepository userRepository;


    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if(validEmail(user)) {
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("email").addConstraintViolation();
            return false;
        }
    }

    boolean validEmail(User user) {
        if (user.getId() == null) {
            return userRepository.findByEmail(user.getEmail()) == null;
        } else {
            return user.getId().equals(userRepository.findByEmail(user.getEmail()).getId());
        }
    }
}
