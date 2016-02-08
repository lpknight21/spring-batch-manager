package com.edo.person.controller;

import com.edo.person.model.Person;
import com.edo.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("people")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping()
    public List<Person> getPeople() {
        return personService.getPeople();
    }

    @RequestMapping(value = "{id}")
    public Person getPerson(@PathVariable long id) {
        return personService.getPerson(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public long createPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }
}
