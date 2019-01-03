package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.InMemoryDB;
import com.hsbc.plitter.domain.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.json.Json;
import javax.json.JsonObject;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by garga9 on 21/12/2018.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestPleetServiceTest {

    @Autowired
    MockMvc mockMvc;

    private MediaType jsonMediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @MockBean
    private UserService userServiceTest;

    @Before
    public void setUp() throws Exception {
        InMemoryDB.Cleanup();

    }

    @Test
    public void when_PostedAValidPleet_ReturnAValidResponse() throws Exception {

        mockMvc.perform(post("/messages").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMockMessageForTheTextProvidedAndUserDetails("Mock Message", "1", "adam").toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void when_PostedAValidPleet_ReturnThePostedPleetWithTimeStamp() throws Exception {
        mockMvc.perform(post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMockMessageForTheTextProvidedAndUserDetails("Mock Message", "1", "adam").toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("text", is("Mock Message")))
                .andExpect(MockMvcResultMatchers.jsonPath("postedTime", Matchers.anything()));
    }

    @Test
    public void when_RequestAPleetWithAGivenId_ReturnThePostedPleetFromInMemory() throws Exception {

        mockMvc.perform(post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMockMessageForTheTextProvidedAndUserDetails("Mock Message", "1", "adam").toString()));

        mockMvc.perform(get("/messages/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("text", is("Mock Message")))
                .andExpect(MockMvcResultMatchers.jsonPath("postedTime", anything()));
    }

    @Test
    public void when_CheckingTheWall_ReturnAllThePleetsPostedByTheLoggedinUserInReverseChronologicalOrder() throws Exception{
        User adam = new User(1L, "adam");
        User bob = new User(2L, "bob");

        when(userServiceTest.getLoggedinUser()).thenReturn(adam);

        mockMvc.perform(post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMockMessageForTheTextProvidedAndUserDetails("Pleet 1", "1", "adam").toString()));

        when(userServiceTest.getLoggedinUser()).thenReturn(bob);

        mockMvc.perform(post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMockMessageForTheTextProvidedAndUserDetails("Pleet 2", "2", "bob").toString()));

        when(userServiceTest.getLoggedinUser()).thenReturn(adam);

        mockMvc.perform(post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMockMessageForTheTextProvidedAndUserDetails("Pleet 3", "1", "adam").toString()));

        mockMvc.perform(get("/messages/getAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(3)))
                .andExpect(jsonPath("$.[1].id", is(1)));
    }

    private JsonObject createMockMessageForTheTextProvidedAndUserDetails(String text, String userId, String userName) {
        JsonObject userObject = Json.createObjectBuilder().
                add("userName", userName).
                add("userId", userId).
                build();
        return Json.createObjectBuilder().
                add("text", text).
                add("id", 1).
                add("user", userObject).
                build();
    }
}
