package com.crud.demo.annotation;

import com.crud.demo.validator.BirthDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Constraint(validatedBy = BirthDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BirthDate {
    String message() default "Please send birth date in past";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}