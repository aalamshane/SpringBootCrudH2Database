package com.crud.demo.model;


import com.crud.demo.annotation.BirthDate;
import com.crud.demo.annotation.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
public class UserRequest {
    @NotBlank(message = "First Name is mandatory")
    @NotNull
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @UniqueEmail
    @NotBlank(message = "Email is mandatory")
    private String email;

    @BirthDate(message = "The birth date must be past")
    @NotNull
    private LocalDate dob;

}
