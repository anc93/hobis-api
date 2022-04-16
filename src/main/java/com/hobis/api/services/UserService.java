package com.hobis.api.services;

import com.hobis.api.entities.Users;
import com.hobis.api.hydrator.UsersHydrator;
import com.hobis.api.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getUsers() {
        List<Users> users = new ArrayList<>();
        userRepository.findAll()
                .forEach(users::add);
        return users;
    }

    public Optional<Users> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public void addUsers(Users users) {
        try {
            userRepository.save(users);
        } catch (Exception exception) {
            log.error("Error occurred while adding user: " + exception);
        }
    }

    public int updateUser(Integer id, Users updateUserData) {
        try {
            Optional<Users> userToUpdate = getUserById(id);
            if (userToUpdate.isEmpty()){
                return 0;
            }
            UsersHydrator usersHydrator = new UsersHydrator();
            Users user = usersHydrator.hydrateUser(userToUpdate.get(), updateUserData);
            userRepository.save(user);

            return 1;
        } catch (Exception exception) {
            log.error("Error occurred while adding user: " + exception);
            return 0;
        }
    }

    public int deleteUser(Integer id) {
        Optional<Users> userToDelete = getUserById(id);
        if (userToDelete.isEmpty()) {
            return 0;
        }
        userRepository.delete(userToDelete.get());

        return 1;
    }
}
