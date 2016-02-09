package com.edo.user.controller;

import com.edo.user.model.User;
import com.edo.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping()
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @RequestMapping(value = "{id}")
    public User getUser(@PathVariable long id) {
        return userDAO.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public long createUser(@RequestBody User user) {
        return userDAO.addUser(user);
    }
}
