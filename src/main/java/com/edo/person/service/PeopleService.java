package com.edo.person.service;

import com.edo.person.mapper.PersonMapper;
import com.edo.person.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {

    @Autowired
    private PersonMapper personMapper;

    public long addPerson(Person person) {
        return personMapper.addPerson(person);
    }

    public Person getPerson(long id) {
        return personMapper.getPerson(id);
    }

    public List<Person> getAll() {
        return personMapper.getAll();
    }
}
