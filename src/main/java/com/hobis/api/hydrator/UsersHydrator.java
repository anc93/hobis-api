package com.hobis.api.hydrator;

import com.hobis.api.entities.Users;

public class UsersHydrator {

    public Users hydrateUser(Users user, Users updateUserData) {
        if (updateUserData.getUsername() != null) {
            user.setUsername(updateUserData.getUsername());
        }
        if (updateUserData.getTotalScore() != null) {
            user.setTotalScore(updateUserData.getTotalScore());
        }

        return user;
    }
}
