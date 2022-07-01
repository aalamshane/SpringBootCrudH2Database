package com.crud.demo.controller;

import com.crud.demo.exception.UserNotFoundException;
import com.crud.demo.model.User;
import com.crud.demo.model.UserRequest;
import com.crud.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createNewUser(@Valid @RequestBody UserRequest userRequest) {
        Long primaryKey = userService.createNewUser(userRequest);
        return  "User " + HttpStatus.CREATED + " & user id:" + primaryKey;
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable long id, @Valid @RequestBody UserRequest userRequest) throws UserNotFoundException {
        User user = userService.updateUser(id, userRequest);
        return  user;
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return  "User deleted";
    }

    @GetMapping("/find/{id}")
    public User findUser(@PathVariable long id) throws UserNotFoundException {
        User user = userService.findUser(id);
        return  user;
    }
}
