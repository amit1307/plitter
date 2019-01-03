package com.hsbc.plitter.service.rest;

import com.hsbc.plitter.domain.Pleet;
import com.hsbc.plitter.domain.User;
import com.hsbc.plitter.service.rest.FollowService;
import com.hsbc.plitter.service.rest.PleetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by garga9 on 02/01/2019.
 */
@RestController
@RequestMapping("/timeline")
public class TimelineService {

    private FollowService followService;

    private PleetService pleetService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Pleet> getTimelineMessages() {
        return followService.getAllFollowedUserForTheLoggedInUser().stream()
                                            .flatMap(user -> pleetService.getAllByUser(user).stream())
                                            .sorted(Comparator.comparing(Pleet::getPostedTime).reversed())
                                            .collect(Collectors.toList());
    }

    @Autowired
    public void setFollowService(FollowService followService) {
        this.followService = followService;
    }

    @Autowired
    public void setPleetService(PleetService pleetService) {
        this.pleetService = pleetService;
    }
}
