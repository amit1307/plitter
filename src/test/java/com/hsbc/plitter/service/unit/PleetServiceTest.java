package com.hsbc.plitter.service.unit;

import com.hsbc.plitter.domain.Pleet;
import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.exceptions.InvalidArgumentException;
import com.hsbc.plitter.service.rest.PleetService;
import com.hsbc.plitter.service.rest.UserService;
import com.hsbc.plitter.repo.PleetRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by garga9 on 20/12/2018.
 */
@RunWith(JUnit4.class)
public class PleetServiceTest {

    private static final String LONG_PLEET = "123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789";

    private PleetRepo pleetRepo;
    private UserService userService;


    @Before
    public void setUp() throws Exception {
        pleetRepo = Mockito.mock(PleetRepo.class);
        userService = Mockito.mock(UserService.class);
    }

    @Test(expected = InvalidArgumentException.class)
    public void when_PleetIsEmpty_ReturnError() throws Exception {
        PleetService pleetService = new PleetService();
        pleetService.postPleet(new Pleet(""));
    }

    @Test(expected = InvalidArgumentException.class)
    public void when_PleetIsNull_ReturnError() throws Exception {
        PleetService pleetService = new PleetService();
        pleetService.postPleet(new Pleet(null));
    }

    @Test(expected = InvalidArgumentException.class)
    public void when_PleetIsMoreThan140Characters_ReturnError() throws Exception {
        PleetService pleetService = new PleetService();
        pleetService.postPleet(new Pleet(LONG_PLEET));
    }

    @Test
    public void when_AValidPleetPosted_SaveAndReturnPleetPosted() throws Exception {
        User user = new User(1, "adam");
        Pleet expectedPleet = new Pleet("The first pleet");
        Pleet pleetToBeSaved = new Pleet("The first pleet");
        when(userService.getLoggedinUser()).thenReturn(user);
        when(pleetRepo.save(pleetToBeSaved)).thenReturn(expectedPleet);
        PleetService pleetService = new PleetService();
        pleetService.setUserService(userService);
        pleetService.setPleetRepo(pleetRepo);
        Pleet pleet = pleetService.postPleet(new Pleet("The first pleet"));
        assertEquals(expectedPleet, pleet);
    }

    @Test
    public void when_AValidPleetPosted_SaveAndReturnPleetPostedWIthUserIdAsLoggedInUser() throws Exception {
        User user = new User(1, "adam");
        when(userService.getLoggedinUser()).thenReturn(user);
        Pleet expectedPleet = new Pleet("The first pleet");
        expectedPleet.setUser(user);
        Pleet pleetToBeSaved = new Pleet("The first pleet");
        when(pleetRepo.save(pleetToBeSaved)).thenReturn(expectedPleet);
        PleetService pleetService = new PleetService();
        pleetService.setPleetRepo(pleetRepo);
        pleetService.setUserService(userService);
        Pleet pleet = pleetService.postPleet(new Pleet("The first pleet"));
        assertEquals(expectedPleet, pleet);
        assertTrue(pleet.getUser().getUserId() == user.getUserId());
    }
}
