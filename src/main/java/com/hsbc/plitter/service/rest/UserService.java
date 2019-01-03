package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Comparator;
import java.util.List;

/**
 * Created by garga9 on 27/12/2018.
 */
@RestController
@RequestMapping("/userService")
public class UserService {

    private UserRepo userRepo;

    private String loggedinUser;

    @RequestMapping(path = "/loggedIn", method = RequestMethod.GET)
    public User getLoggedinUser() {
        User byName = userRepo.findByName(loggedinUser);
        if (byName == null) {
            List<User> all = userRepo.findAll();
            User newUser = new User(all.stream().map(User::getUserId).max(Comparator.comparingLong(value -> value)).orElse(1L)+1L, loggedinUser);
            all.add(newUser);
            return newUser;
        }
        return userRepo.findByName(loggedinUser);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void setLoggedinUser(@PathParam("setLoggedInUser")String setLoggedInUser) {
        this.loggedinUser = setLoggedInUser;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}
