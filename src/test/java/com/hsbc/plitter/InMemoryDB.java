package com.hsbc.plitter;

import com.hsbc.plitter.repo.FollowUserRepo;
import com.hsbc.plitter.repo.PleetRepo;

/**
 * Created by garga9 on 02/01/2019.
 */
public class InMemoryDB {

    public static void Cleanup() {

        PleetRepo.cleanUp();
        FollowUserRepo.cleanUp();
    }
}
