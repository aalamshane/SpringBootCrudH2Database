package com.crud.demo;

import com.crud.demo.controller.UserController;
import com.crud.demo.model.User;
import com.crud.demo.model.UserRequest;
import com.crud.demo.repository.UserRepo;
import com.crud.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserServiceTest {



    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepo userRepo;

    private LocalDate date = LocalDate.now();
    @Test
    public void shouldCreateANewUser() throws Exception {

        UserRequest userRequest = getUserRequest();
        User user = getUser(userRequest);
        when(userRepo.save(user)).thenReturn(user);
        Long id = userService.createNewUser(userRequest);
        assertThat(id, is(0L));

    }
    @Test
    public void shouldUpdateAUser() throws Exception {

        UserRequest userRequest = getUserRequest();
        User user = getUser(userRequest);
        when(userRepo.findById(0l)).thenReturn(java.util.Optional.of(user));
        User userResp = userService.updateUser(0L,userRequest);
        assertThat(userResp.getId(), is(0L));

    }
    @Test
    public void shouldDeleteAUser() throws Exception {

        UserRequest userRequest = getUserRequest();
        User user = getUser(userRequest);
        when(userRepo.findById(0l)).thenReturn(java.util.Optional.of(user));
        userService.deleteUser(0L);

    }
    @Test
    public void shouldFindAUser() throws Exception {

        UserRequest userRequest = getUserRequest();
        User user = getUser(userRequest);
        when(userRepo.findById(0l)).thenReturn(java.util.Optional.of(user));
        User userDBResp = userService.findUser(0L);
        assertThat(userDBResp.getFirstName(), is("Shane"));
    }

    private User getUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDob(userRequest.getDob());
        user.setEmailId(userRequest.getEmail());
        return user;
    }

    private UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Shane");
        userRequest.setLastName("Aalam");
        userRequest.setEmail("dummy@email.com");
        userRequest.setDob(date);
        return userRequest;
    }
}
