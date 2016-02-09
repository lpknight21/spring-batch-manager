package com.edo.user.dao;

import com.edo.user.mapper.UserMapper;
import com.edo.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDAO {

    @Autowired private UserMapper userMapper;

    public long addUser(User user) {
        return userMapper.addUser(user);
    }

    public User getUser(long id) {
        return userMapper.getUser(id);
    }

    public List<User> getUsers() {
        return userMapper.getUsers();
    }
}
