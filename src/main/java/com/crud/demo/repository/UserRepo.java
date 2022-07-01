package com.crud.demo.repository;

import com.crud.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmailId(String email);
}
