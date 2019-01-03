package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.repo.FollowUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by garga9 on 27/12/2018.
 */
@RestController
@RequestMapping("/follow")
public class FollowService {

    private UserService userServie;

    private FollowUserRepo followUserRepo;

    @RequestMapping(method = RequestMethod.POST)
    public User follow(@PathParam("userName")String userName) {
        return followUserRepo.saveForLoggedInUser(userServie.getLoggedinUser().getUserId(), userName);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllFollowedUserForTheLoggedInUser() {
        return followUserRepo.getAllForLoggedInUser(userServie.getLoggedinUser());
    }

    @RequestMapping(path = "/{userName}", method = RequestMethod.GET)
    public List<User> getAllFollowedUserForTheUserName(@PathVariable("userName") String userName) {
        return followUserRepo.getAllForTheUser(userName);
    }

    @Autowired
    public void setFollowUserRepo(FollowUserRepo followUserRepo) {
        this.followUserRepo = followUserRepo;
    }

    @Autowired
    public void setUserServie(UserService userServie) {
        this.userServie = userServie;
    }
}
