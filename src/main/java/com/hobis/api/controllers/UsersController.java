package com.hobis.api.controllers;

import com.hobis.api.entities.Users;
import com.hobis.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("api/users")
@RestController
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getAllUsers() {
        return this.userService.getUsers();
    }

    @GetMapping(path = "{id}")
    public Users getUserById(@PathVariable("id") Integer id) {
        return this.userService.getUserById(id)
                .orElse(null);
    }

    @PostMapping
    public void addPerson(@RequestBody Users users) {
        this.userService.addUsers(users);
    }

    @PutMapping(path = "{id}")
    public int updateUserById(@PathVariable("id") Integer id, @RequestBody Users user) {
        return this.userService.updateUser(id, user);
    }

    @DeleteMapping(path = "{id}")
    public int deleteUserById(@PathVariable("id") Integer id) {
        return this.userService.deleteUser(id);
    }
}
