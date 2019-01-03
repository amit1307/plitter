package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.repo.UserRepo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by garga9 on 02/01/2019.
 */
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class RestUserServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepo userRepo;

    private MediaType jsonMediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        userRepo = Mockito.mock(UserRepo.class);
    }

    @Test
    public void when_SetLoggedInUser_ReturnGetShouldReturnTheSetUser() throws Exception{
        when(userRepo.findByName("john")).thenReturn(new User(2l, "john"));
        mockMvc.perform(post("/userService?setLoggedInUser=john"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(get("/userService/loggedIn"))
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("userName", is("john")));
    }

    @Test
    public void when_UserBeingSetDoesnotExist_ReturnAddTheUserInUserRepoAndReturnAddedUser() throws Exception {
        when(userRepo.findByName("newUser")).thenReturn(null);
        mockMvc.perform(post("/userService?setLoggedInUser=newUser"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(get("/userService/loggedIn"))
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("userName", is("newUser")));
    }

    @Test@Ignore
    public void when_GetAllTheExistingUsers_ReturnAllUsers() throws Exception{
        when(userRepo.findAll()).thenReturn(Arrays.asList(new User(1L, "adam"), new User(2L, "john")));
        mockMvc.perform(get("/userService"))
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }
}
