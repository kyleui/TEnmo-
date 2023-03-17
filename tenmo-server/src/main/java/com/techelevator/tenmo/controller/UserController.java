package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {


    @Autowired
    UserDao userDao;

    //get list of all users - this works!
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.findAll();
    }

    //get specific user by id - this works!
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable int id) {
        return userDao.getUserById(id);
    }

}
