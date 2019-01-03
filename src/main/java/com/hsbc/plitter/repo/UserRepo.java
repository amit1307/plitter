package com.hsbc.plitter.repo;

import com.hsbc.plitter.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garga9 on 28/12/2018.
 */
@Repository
public class UserRepo {

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1L, "adam"));
        users.add(new User(2L, "bob"));
        users.add(new User(3L, "john"));
        users.add(new User(4L, "duke"));
    }

    public User findByName(String userNameToFollow) {
        return users.stream()
                .filter(user -> user.getUserName().equals(userNameToFollow))
                .findAny()
                .orElse(null);
    }

    public List<User> findAll() {
        return users;
    }
}
