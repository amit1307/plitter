package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.InMemoryDB;
import com.hsbc.plitter.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by garga9 on 28/12/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestFollowServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private MediaType jsonMediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        InMemoryDB.Cleanup();
    }

    @Test
    public void when_UserFollowsAnotherUser_ReturnSuccessfullyFollowed() throws Exception {

        when(userService.getLoggedinUser()).thenReturn(new User(1, "adam"));

        mockMvc.perform(post("/follow?userName=bob")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType));
    }

    @Test
    public void when_UserChecksAllFollowingUsers_ReturnListOfAllUserFollowedByTheLoggedInUser() throws Exception {

        when(userService.getLoggedinUser()).thenReturn(new User(1, "adam"));

        mockMvc.perform(post("/follow?userName=bob")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/follow?userName=john")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/follow?userName=duke")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/follow"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userId", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].userId", is(2)));
    }

    @Test
    public void when_LoggedInUserFollowAnotherUserWithoutTheTargetUserFollowingLoggedInUser_ReturnTheTargetUserFollowedByTheLoggedInUser() throws Exception {

        when(userService.getLoggedinUser()).thenReturn(new User(1, "adam"));

        mockMvc.perform(get("/follow/bob"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));

        mockMvc.perform(post("/follow?userName=bob")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/follow"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId", is(2)));
    }

    @Test
    public void when_LoggedInUserFollowTheAlreadyFollowedUser_ReturnTheFollowedUserWithoutDoingAnythingElse()throws Exception{
        User user = new User(1, "adam");
        when(userService.getLoggedinUser()).thenReturn(user);

        mockMvc.perform(post("/follow?userName=bob")
                .contentType(MediaType.APPLICATION_JSON));


        mockMvc.perform(post("/follow?userName=bob")
                .contentType(MediaType.APPLICATION_JSON));


        mockMvc.perform(get("/follow"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }
}
