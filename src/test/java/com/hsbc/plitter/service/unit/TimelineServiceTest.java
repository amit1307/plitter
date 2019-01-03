package com.hsbc.plitter.service.unit;

import com.hsbc.plitter.domain.Pleet;
import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.exceptions.InvalidArgumentException;
import com.hsbc.plitter.service.rest.TimelineService;
import com.hsbc.plitter.service.rest.FollowService;
import com.hsbc.plitter.service.rest.PleetService;
import com.hsbc.plitter.service.rest.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by garga9 on 28/12/2018.
 */

@RunWith(JUnit4.class)
public class TimelineServiceTest {


    private UserService userService;
    private FollowService followService;
    private PleetService pleetService;


    @Before
    public void setUp() throws Exception {
        userService = Mockito.mock(UserService.class);
        followService = Mockito.mock(FollowService.class);
        pleetService = Mockito.mock(PleetService.class);
    }

    @Test
    public void when_LoggedInUserCheckTheTimeline_ReturnAllTheMessagesByFollowedUsers() throws InvalidArgumentException {
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

        TimelineService timelineService = new TimelineService();
        timelineService.setFollowService(followService);
        timelineService.setPleetService(pleetService);
        List<Pleet> messages = timelineService.getTimelineMessages();
        assertTrue(messages.size() == 2);
    }
}
