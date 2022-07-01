package com.crud.demo.service;

import com.crud.demo.exception.UserNotFoundException;
import com.crud.demo.model.User;
import com.crud.demo.model.UserRequest;
import com.crud.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public Long createNewUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDob(userRequest.getDob());
        user.setEmailId(userRequest.getEmail());
        user = userRepo.save(user);
        return user.getId();
    }

    @Transactional
    public User updateUser(Long id, UserRequest userRequest) throws UserNotFoundException {

        Optional<User> userFromDb = userRepo.findById(id);

        if (userFromDb.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id: '%s' not found", id));
        }

        User userToUpdate = userFromDb.get();

        userToUpdate.setFirstName(userRequest.getFirstName());
        userToUpdate.setLastName(userRequest.getLastName());
        userToUpdate.setDob(userRequest.getDob());
        userToUpdate.setEmailId(userRequest.getEmail());

        return userToUpdate;
    }

    public void deleteUser(long id) {
        Optional<User> userFromDb = userRepo.findById(id);
        if (userFromDb.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id: '%s' not found", id));
        }
        userRepo.deleteById(id);
    }

    public User findUser(long id) throws UserNotFoundException {
        Optional<User> userFromDb = userRepo.findById(id);
        if (userFromDb.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id: '%s' not found", id));
        }
        return userFromDb.get();
    }
}
