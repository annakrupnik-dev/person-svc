package com.example.person.controller;

import com.example.person.domain.Person;
import com.example.person.domain.PersonDataWrapper;
import com.example.person.repos.PersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PersonController {

    private final static Logger logger =LoggerFactory.getLogger(PersonController.class);

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDataWrapper getAllPersons() {
        Iterable<Person> persons = personRepository.findAll();
        PersonDataWrapper personDataWrapper = new PersonDataWrapper();
        personDataWrapper.setPersons((List<Person>) persons);
        return personDataWrapper;
    }


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Person> persons = personRepository.findAll();
        model.put("persons", persons);
        return "main";
    }

    @PostMapping
    public String addPerson(@RequestParam String name,
                            @RequestParam String gender,
                            @RequestParam Integer age,
                            @RequestParam Integer height,
                            @RequestParam Integer weight,
                            Map<String, Object> model) {
        Person person = new Person(name,age,gender,height,weight);
        personRepository.save(person);
        Iterable<Person> persons = personRepository.findAll();
        model.put("persons", persons);
        return "main";
    }
/*

    @GetMapping("/person")
    public String getPersonById(@RequestParam Integer id,
                                Map<String, Object> model) {

        Optional<Person> person = personRepository.findById(id);
        List<Optional<Person>> persons = new ArrayList<>();
        if (person!=null)
            persons.add(person);
        model.put("persons", persons);
        return "main";
    }
*/

    @GetMapping("/personByName")
    public String getPersonByName(@RequestParam String name,
                                Map<String, Object> model) {
        Iterable<Person> persons;
        if(!StringUtils.isEmpty(name)) {
            persons = personRepository.findByName(name);
        } else {
            persons = personRepository.findAll();
        }
        model.put("persons", persons);
        return "main";
    }

}
