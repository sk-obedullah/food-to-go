package com.ftg.accountservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ftg.accountservice.model.User;
import com.ftg.accountservice.service.UserService;

public class UserControllerAdminTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerAdmin userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L,"name1", "user1@example.com", "password","12345", "City 1", "admin"));
        userList.add(new User(2L,"name2", "user2@example.com", "password","12345", "City 2", "admin"));
        when(userService.getAll()).thenReturn(userList);

        mockMvc.perform(get("/api/admin/user-service"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].userId").value(1))
               .andExpect(jsonPath("$[0].userEmail").value("user1@example.com"))
               .andExpect(jsonPath("$[0].userName").value("name1"))
               .andExpect(jsonPath("$[0].userCity").value("City 1"))
               .andExpect(jsonPath("$[0].userMobile").value("12345"))
               .andExpect(jsonPath("$[1].userId").value(2))
               .andExpect(jsonPath("$[1].userEmail").value("user2@example.com"))
               .andExpect(jsonPath("$[1].userName").value("name2"))
               .andExpect(jsonPath("$[1].userCity").value("City 2"))
               .andExpect(jsonPath("$[1].userMobile").value("12345"));

        verify(userService, times(1)).getAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetUserByUserName() throws Exception {
        User user = new User(1L,"name1", "user1@example.com", "password","12345", "City 1", "admin");

        when(userService.getUserByuserName("user1@example.com")).thenReturn(user);

        mockMvc.perform(get("/api/admin/user-service/user/{email}", "user1@example.com"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.email").value("user1@example.com"))
               .andExpect(jsonPath("$.name").value("name1"))
               .andExpect(jsonPath("$.userCity").value("City 1"))
               .andExpect(jsonPath("$.userMobile").value("12345"));

        verify(userService, times(1)).getUserByuserName("user1@example.com");
        verifyNoMoreInteractions(userService);
    }
}
