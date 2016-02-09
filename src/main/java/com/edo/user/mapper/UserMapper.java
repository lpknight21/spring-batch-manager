package com.edo.user.mapper;

import com.edo.user.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    User getUser(long userId);

    @Insert("INSERT INTO user (first_name, last_name) VALUES (#{firstName}, #{lastName})")
    long addUser(User user);

    @Select("SELECT first_name, last_name FROM user WHERE created_timestamp >= #{timestamp}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    List<User> getUsersByTimestamp(String timestamp);

    @Select("SELECT id, first_name, last_name FROM user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    List<User> getUsers();
}
