package com.hobis.api.unit.entities;

import com.hobis.api.configuration.AbstractTestCase;
import com.hobis.api.entities.Users;
import org.junit.Assert;
import org.junit.Test;

public class UsersTests extends AbstractTestCase {

    @Test
    public void testUserCreatedWithCorrectData() {
        Integer userId = 1;
        Double totalScore = Double.valueOf(getTimeStampString());
        String userName = "test user " + totalScore;

        Users users = new Users(1, userName, totalScore);

        Assert.assertEquals(userId, users.getId());
        Assert.assertEquals(userName, users.getUsername());
        Assert.assertEquals(totalScore, users.getTotalScore());
    }

    @Test
    public void testManipulateUserWillReflectCorrectData() {
        Double totalScore = Double.valueOf(getTimeStampString());
        String userName = "test user " + totalScore;

        Users users = new Users();
        users.setUsername(userName);
        users.setTotalScore(totalScore);

        Assert.assertEquals(userName, users.getUsername());
        Assert.assertEquals(totalScore, users.getTotalScore());
    }
}
