package com.example.person.service;

import com.example.person.domain.Person;
import com.example.person.domain.PersonDataWrapper;
import com.example.person.exception.ResourceNotFoundException;
import com.example.person.repos.AddressRepository;
import com.example.person.repos.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService{

    private PersonRepository personRepository;
    private AddressRepository addressRepository;

    @Override
    public PersonDataWrapper getAllPersons() {
        Iterable<Person> persons = personRepository.findAll();
        PersonDataWrapper personDataWrapper = new PersonDataWrapper();
        personDataWrapper.setPersons((List<Person>) persons);
        return personDataWrapper;
    }

    @Override
    public Person getPersonById(Integer personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("PersonId " + personId + "not found"));
    }

    @Override
    public Person updatePerson(Integer addressId, Integer personId, Person inputData) {
        if(!addressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("AddressId " + addressId + " not found");
        }

        return personRepository.findById(personId).map(person -> {
            person.setName(inputData.getName());
            person.setHeight(inputData.getHeight());
            person.setGender(inputData.getGender());
            person.setWeight(inputData.getWeight());
            person.setAge(inputData.getAge());
            return personRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("PersonId " + personId + "not found"));
    }

    @Override
    public Person createPerson(Integer addressId, Person person) {
        return addressRepository.findById(addressId).map(address -> {
            person.setAddress(address);
            return personRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + " not found"));
    }

    @Override
    public boolean deletePerson(Integer addressId, Integer personId) {
        return personRepository.findByIdAndAddressId(personId, addressId).map(person -> {
            personRepository.delete(person);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + personId + " and addressId " + addressId));
    }
}
