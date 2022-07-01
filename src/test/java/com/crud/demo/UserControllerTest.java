package com.crud.demo;

import com.crud.demo.controller.UserController;
import com.crud.demo.exception.UserNotFoundException;
import com.crud.demo.model.User;
import com.crud.demo.model.UserRequest;
import com.crud.demo.repository.UserRepo;
import com.crud.demo.service.UserService;
import com.crud.demo.validator.UniqueEmailValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UniqueEmailValidator uniqueEmailValidator;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private ObjectMapper objectMapper;


    @Captor
    private ArgumentCaptor<UserRequest> userRequestArgumentCaptor;

    private LocalDate date = LocalDate.now();
    @Test
    public void postingShouldCreateANewUser() throws Exception {

        UserRequest userRequest = getUserRequest();
        when(userService.createNewUser(userRequestArgumentCaptor.capture())).thenReturn(1L);
        when(userRepo.findByEmailId("dummy@email.com")).thenReturn(null);

        this.mockMvc
                .perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)));


        assertThat(userRequest.getFirstName(), is("Shane"));
        assertThat(userRequest.getLastName(), is("Aalam"));
        assertThat(userRequest.getEmail(), is("dummy@email.com"));
        assertThat(userRequest.getDob(), is(date));

    }



    @Test
    public void postingShouldCreateANewUserWithEmptyFirstName() throws Exception {
        UserRequest userRequest = getUserRequest();
        userRequest.setFirstName("");
        when(userService.createNewUser(userRequestArgumentCaptor.capture())).thenReturn(1L);
        when(userRepo.findByEmailId("dummy@email.com")).thenReturn(null);

        this.mockMvc
                .perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))).andExpect(status().isBadRequest());

    }

    @Test
    public void updateShouldUpdateUser() throws Exception, UserNotFoundException {

        UserRequest userRequest = getUserRequest();
        User user = getUserObject(userRequest);
        when(userService.updateUser(eq(0l), userRequestArgumentCaptor.capture())).thenReturn(user);
        when(userRepo.findByEmailId("dummy@email.com")).thenReturn(null);

        this.mockMvc
                .perform(put("/api/user/update/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)));


        assertThat(userRequest.getFirstName(), is("Shane"));
        assertThat(userRequest.getLastName(), is("Aalam"));
        assertThat(userRequest.getEmail(), is("dummy@email.com"));
        assertThat(userRequest.getDob(), is(date));

    }

    @Test
    public void findShouldFindUser() throws Exception, UserNotFoundException {

        UserRequest userRequest = getUserRequest();
        User user = getUserObject(userRequest);
        when(userService.findUser(eq(0l))).thenReturn(user);

        this.mockMvc
                .perform(get("/api/user/find/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)));


        assertThat(userRequest.getFirstName(), is("Shane"));
        assertThat(userRequest.getLastName(), is("Aalam"));
        assertThat(userRequest.getEmail(), is("dummy@email.com"));
        assertThat(userRequest.getDob(), is(date));

    }

    @Test
    public void deleteShouldThrowAnException() throws Exception {

        UserRequest userRequest = getUserRequest();
        getUserObject(userRequest);
        doThrow(UserNotFoundException.class).when(userService).deleteUser(1l);

        this.mockMvc
                .perform(delete("/api/user/delete/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)));


        assertThat(userRequest.getFirstName(), is("Shane"));
        assertThat(userRequest.getLastName(), is("Aalam"));
        assertThat(userRequest.getEmail(), is("dummy@email.com"));
        assertThat(userRequest.getDob(), is(date));

    }
    private UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Shane");
        userRequest.setLastName("Aalam");
        userRequest.setEmail("dummy@email.com");
        userRequest.setDob(date);
        return userRequest;
    }
    private User getUserObject(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDob(userRequest.getDob());
        user.setEmailId(userRequest.getEmail());
        return user;
    }
}
