package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.domain.Pleet;
import com.hsbc.plitter.domain.User;
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
import java.util.Arrays;
import java.util.Collections;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by garga9 on 02/01/2019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RestTimelineServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private FollowService followService;

    @MockBean
    private PleetService pleetService;

    private MediaType jsonMediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void when_LoggedInUserAccessTimeline_ReturnStatusOk() throws Exception {
        mockMvc.perform(get("/timeline"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void when_LoggedInUserAccessTimeline_ReturnMessagesPostedByFollowedUsersInReverseChronoOrder() throws Exception {
        User bob = new User(2, "bob");
        User john = new User(3, "john");
        User duke = new User(4, "duke");
        Pleet pleetBybob = new Pleet("PleetBybob");
        Pleet pleetByjohn = new Pleet("PleetByjohn");
        Pleet pleetByduke = new Pleet("PleetByduke");

        when(userService.getLoggedinUser()).thenReturn(new User(1, "adam"));
        when(followService.getAllFollowedUserForTheLoggedInUser()).thenReturn(Arrays.asList(bob, john));
        when(pleetService.getAllByUser(bob)).thenReturn(Collections.singletonList(pleetBybob));
        when(pleetService.getAllByUser(john)).thenReturn(Collections.singletonList(pleetByjohn));
        when(pleetService.getAllByUser(duke)).thenReturn(Collections.singletonList(pleetByduke));

        mockMvc.perform(get("/timeline"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(jsonMediaType))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].text", is("PleetBybob")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].text", is("PleetByjohn")));

    }
}
