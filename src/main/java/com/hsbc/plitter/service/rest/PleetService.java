package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.domain.Pleet;
import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.exceptions.InvalidArgumentException;
import com.hsbc.plitter.repo.PleetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by garga9 on 20/12/2018.
 */
@RestController
@RequestMapping("/messages")
public class PleetService {

    private PleetRepo pleetRepo;

    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public Pleet postPleet(@RequestBody Pleet pleet) throws InvalidArgumentException {
        if (pleet.getText() == null || pleet.getText().isEmpty()) {
            throw new InvalidArgumentException("Cannot post an empty pleet");
        }
        else if (pleet.getText().length() > 140) {
            throw new InvalidArgumentException("Cannot post a pleet more than 140 characters");
        }
        else {
            pleet.setUser(userService.getLoggedinUser());
            return pleetRepo.save(pleet);
        }
    }

    @RequestMapping(path = "/{pleetId}", method = RequestMethod.GET)
    public Pleet getPleetById(@PathVariable("pleetId")Long pleetId) {
        return pleetRepo.getById(pleetId);
    }

    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    public List<Pleet> getAll() {
        return pleetRepo.findAllPostedByLoggedInUser(userService.getLoggedinUser());
    }

    public List<Pleet> getAllByUser(User user) {
        return pleetRepo.findAllPostedByLoggedInUser(user);
    }

    @Autowired
    public void setPleetRepo(PleetRepo pleetRepo) {
        this.pleetRepo = pleetRepo;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
