package com.hsbc.plitter.repo.unit;

import com.hsbc.plitter.domain.Pleet;
import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.service.rest.UserService;
import com.hsbc.plitter.repo.PleetRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by garga9 on 31/12/2018.
 */
@RunWith(JUnit4.class)
public class PleetRepoTest {

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = Mockito.mock(UserService.class);
    }

    @Test
    public void when_SaveAPleet_ReturnLoggedInUserAsAuthorOfThePleet() {
        User user = new User(1, "adam");
        when(userService.getLoggedinUser()).thenReturn(user);

        PleetRepo pleetRepo = new PleetRepo();
        pleetRepo.setUserService(userService);
        Pleet pleet = pleetRepo.save(new Pleet("Testing"));
        assertNotNull(pleet.getUser());
        assertEquals(user, pleet.getUser());
    }
}
