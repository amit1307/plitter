package com.hsbc.plitter.repo;

import com.hsbc.plitter.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by garga9 on 28/12/2018.
 */

@Repository
public class FollowUserRepo {

    private static Map<Long, List<User>> followedUsers = new HashMap<>();

    @Autowired
    private UserRepo userRepo;

    public List<User> getAllForLoggedInUser(User loggedinUser) {
        if (followedUsers.get(loggedinUser.getUserId()) == null) return new ArrayList<>();

        return followedUsers.get(loggedinUser.getUserId()).stream()
                                                            .sorted(Comparator.comparing(User::getUserId).reversed())
                                                            .collect(Collectors.toList());
    }

    public User saveForLoggedInUser(Long loggedInUserId, String userNameToFollow) {
        User userToFollow = userRepo.findByName(userNameToFollow);
        List<User> exisitngFollowedUsers = followedUsers.get(loggedInUserId);

        boolean userAlreadyNotFollowed = exisitngFollowedUsers == null || (exisitngFollowedUsers.stream()
                                                                                                .filter(user -> user.getUserId() == userToFollow.getUserId())
                                                                                                .findAny()
                                                                                                .orElse(null) == null);
        if (userAlreadyNotFollowed) {
            followUser(loggedInUserId, userToFollow, exisitngFollowedUsers);
        }
        return followedUsers.get(loggedInUserId).stream()
                .filter(user -> user.getUserId() == userToFollow.getUserId())
                .findAny()
                .orElse(null);
    }

    private void followUser(Long loggedInUserId, User userToFollow, List<User> exisitngFollowedUsers) {
        if (exisitngFollowedUsers != null) {
            exisitngFollowedUsers.add(userToFollow);
        }
        else {
            exisitngFollowedUsers = new ArrayList<>();
            exisitngFollowedUsers.add(userToFollow);
            followedUsers.put(loggedInUserId, exisitngFollowedUsers);
        }
    }

    public List<User> getAllForTheUser(String userName) {
        User userToFollow = userRepo.findByName(userName);

        if (followedUsers.get(userToFollow.getUserId()) == null) return new ArrayList<>();

        return followedUsers.get(userToFollow.getUserId()).stream()
                                                    .sorted(Comparator.comparing(User::getUserId).reversed())
                                                    .collect(Collectors.toList());

    }
    //Not be deployed in prod, only for testing
    public static void cleanUp() {
        followedUsers.clear();
    }
}
