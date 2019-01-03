package com.hsbc.plitter.service.unit;

import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.repo.FollowUserRepo;
import com.hsbc.plitter.service.rest.FollowService;
import com.hsbc.plitter.service.rest.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by garga9 on 27/12/2018.
 */
@RunWith(JUnit4.class)
public class FollowServiceTest {

    private FollowUserRepo followUserRepo;
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        followUserRepo = Mockito.mock(FollowUserRepo.class);
        userService = Mockito.mock(UserService.class);
    }

    @Test
    public void when_AnUserFollowAnotherUser_AllowSuccessfulFollow() {
        User bob = new User(2L, "bob");
        User adam = new User(1, "adam");
        when(followUserRepo.saveForLoggedInUser(1L, "bob")).thenReturn(bob);
        when(userService.getLoggedinUser()).thenReturn(adam);

        FollowService followService = new FollowService();
        followService.setFollowUserRepo(followUserRepo);
        followService.setUserServie(userService);
        User user = followService.follow("bob");
        assertNotNull(user);
        assertTrue(user.getUserName().equals("bob"));
    }
}
