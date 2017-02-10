package com.saniaky.controller;

import com.saniaky.entity.Phone;
import com.saniaky.entity.User;
import com.saniaky.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author saniaky
 * @since 2/10/17
 */
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public List<User> getUsers(@PageableDefault Pageable pageable) {
        return usersService.getUsers(pageable);
    }

    @GetMapping("/{userId}")
    public User getUsers(@PathVariable long userId) {
        return usersService.get(userId);
    }

    @PostMapping("")
    public User addUser(@RequestBody User user) {
        return usersService.create(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(long userId) {
        usersService.delete(userId);
    }

    @GetMapping("/sample")
    public void addSampleData() {
        User saniaky = new User(
                null,
                "saniaky",
                "me@email.com",
                Arrays.asList(
                        new Phone("777", "New York Office"),
                        new Phone("888", "Florida Office"),
                        new Phone("111", "Home")
                ));

        usersService.create(saniaky);
    }
}
