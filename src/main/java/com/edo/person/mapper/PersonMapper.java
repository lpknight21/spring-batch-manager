package com.edo.person.mapper;

import com.edo.person.model.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface PersonMapper {
    @Select("SELECT * FROM people WHERE person_id = #{personId}")
    @Results({
            @Result(property = "personId", column = "person_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    Person getPerson(long personId);

    @Insert("INSERT INTO people (first_name, last_name) VALUES (#{firstName}, #{lastName})")
    long addPerson(Person person);

    @Select("SELECT first_name, last_name FROM people WHERE created_timestamp >= #{timestamp}")
    List<Person> getPeopleByCreatedTimestamp(String timestamp);

    @Select("SELECT person_id, first_name, last_name FROM people")
    @Results({
            @Result(property = "personId", column = "person_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    List<Person> getPeople();
}
