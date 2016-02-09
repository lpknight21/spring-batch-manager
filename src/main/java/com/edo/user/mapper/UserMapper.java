package com.edo.user.mapper;

import com.edo.user.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface UserMapper {

    @Select("SELECT id, first_name, last_name FROM user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    List<User> getUsers();

    @Select("SELECT * FROM user WHERE id = #{userId}")
    @ResultMap("getUsers-void")
    User getUser(long userId);

    @Insert("INSERT INTO user (first_name, last_name) VALUES (#{firstName}, #{lastName})")
    long addUser(User user);

    @Select("SELECT first_name, last_name FROM user WHERE created_timestamp >= #{timestamp}")
    @ResultMap("getUsers-void")
    List<User> getUsersByTimestamp(String timestamp);
}
