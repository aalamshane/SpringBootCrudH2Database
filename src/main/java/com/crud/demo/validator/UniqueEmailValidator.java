package com.crud.demo.validator;

import com.crud.demo.annotation.UniqueEmail;
import com.crud.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    UserRepo userRepo;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepo.findByEmailId(email)==null;
    }
}
