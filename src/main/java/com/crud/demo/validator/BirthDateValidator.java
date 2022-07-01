package com.crud.demo.validator;

import com.crud.demo.annotation.BirthDate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Component
public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        return today.isAfter(valueToValidate);

    }
}
