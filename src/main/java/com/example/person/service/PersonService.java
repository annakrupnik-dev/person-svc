package com.example.person.service;

import com.example.person.domain.Person;
import com.example.person.domain.PersonDataWrapper;

public interface PersonService {
    PersonDataWrapper getAllPersons();
    Person getPersonById(Integer personId);
    Person updatePerson(Integer addressId, Integer personId, Person inputData);
    Person createPerson(Integer addressId, Person person);
    boolean deletePerson(Integer addressId, Integer personId);
}
