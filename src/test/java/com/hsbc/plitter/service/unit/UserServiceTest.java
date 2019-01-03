package com.hsbc.plitter.service.unit;

import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.repo.UserRepo;
import com.hsbc.plitter.service.rest.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by garga9 on 02/01/2019.
 */
@RunWith(JUnit4.class)
public class UserServiceTest {

    UserRepo userRepo;

    @Before
    public void setUp() throws Exception {
        userRepo = Mockito.mock(UserRepo.class);
    }

    @Test
    public void when_SetLoggedInUser_ReturnGetShouldReturnTheSetUser() {
        when(userRepo.findByName("john")).thenReturn(new User(2l, "john"));
        UserService userService = new UserService();
        userService.setUserRepo(userRepo);
        userService.setLoggedinUser("john");
        User loggedinUser = userService.getLoggedinUser();
        assertTrue(loggedinUser.getUserName().equals("john"));
    }

    @Test
    public void when_UserBeingSetDoesnotExist_ReturnAddTheUserInUserRepoAndReturnAddedUser() {
        when(userRepo.findByName("newUser")).thenReturn(null);
        UserService userService = new UserService();
        userService.setUserRepo(userRepo);
        userService.setLoggedinUser("newUser");
        User loggedinUser = userService.getLoggedinUser();
        assertTrue(loggedinUser.getUserName().equals("newUser"));
    }

    @Test
    public void when_GetAllTheExistingUsers_ReturnAllUsers() {
        when(userRepo.findAll()).thenReturn(Arrays.asList(new User(1L, "adam"), new User(2L, "john")));
        UserService userService = new UserService();
        userService.setUserRepo(userRepo);
        List<User> loggedinUser = userService.getAll();
        assertTrue(loggedinUser.size() == 2);
    }
}
