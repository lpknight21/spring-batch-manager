package com.edo.person.controller;

import com.edo.person.model.Person;
import com.edo.person.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @RequestMapping()
    public List<Person> getAll() {
        return peopleService.getAll();
    }

    @RequestMapping(value = "{id}")
    public Person getPerson(@PathVariable long id) {
        return peopleService.getPerson(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public long createPerson(@RequestBody Person person) {
        return peopleService.addPerson(person);
    }
}
