package com.hsbc.plitter.repo;

import com.hsbc.plitter.domain.Pleet;
import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.service.rest.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by garga9 on 25/12/2018.
 */
@Repository
public class PleetRepo {

    public static List<Pleet> pleets = new ArrayList<>();

    UserService userService;

    public Pleet getById(long pleetId) {
        return pleets.stream().filter(pleet -> pleet.getId().equals(pleetId)).findFirst().orElse(null);
    }

    public Pleet save(Pleet pleet) {
        Long maxId = pleets.stream().mapToLong(Pleet::getId).max().orElse(0);
        pleet.setId(maxId + 1);
        pleets.add(pleet);
        return pleet;
    }

    public List<Pleet> findAll() {
        return pleets.stream()
                        .sorted(Comparator.comparing(Pleet::getPostedTime).reversed())
                        .collect(Collectors.toList());
    }

    public List<Pleet> findAllPostedByLoggedInUser(User loggedinUser) {
        return pleets.stream()
                .filter(pleet -> pleet.getUser().getUserId() == loggedinUser.getUserId())
                .sorted(Comparator.comparing(Pleet::getPostedTime).reversed())
                .collect(Collectors.toList());
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //Not be deployed in prod, only for testing
    public static void cleanUp() {
        pleets.clear();
    }
}
