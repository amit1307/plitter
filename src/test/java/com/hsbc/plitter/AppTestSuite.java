package com.hsbc.plitter;

import com.hsbc.plitter.service.rest.RestFollowServiceTest;
import com.hsbc.plitter.service.rest.RestPleetServiceTest;
import com.hsbc.plitter.service.rest.RestTimelineServiceTest;
import com.hsbc.plitter.service.rest.RestUserServiceTest;
import com.hsbc.plitter.service.unit.FollowServiceTest;
import com.hsbc.plitter.service.unit.PleetServiceTest;
import com.hsbc.plitter.service.unit.TimelineServiceTest;
import com.hsbc.plitter.service.unit.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by garga9 on 02/01/2019.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        RestPleetServiceTest.class,
        RestFollowServiceTest.class,
        RestTimelineServiceTest.class,
        RestUserServiceTest.class,
        FollowServiceTest.class,
        PleetServiceTest.class,
        TimelineServiceTest.class,
        UserServiceTest.class})
public class AppTestSuite {
}
