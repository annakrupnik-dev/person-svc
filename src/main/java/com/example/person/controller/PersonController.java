package com.example.person.controller;

import com.example.person.domain.Person;
import com.example.person.domain.PersonDataWrapper;
import com.example.person.exception.ResourceNotFoundException;
import com.example.person.repos.AddressRepository;
import com.example.person.repos.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/addresses")
public class PersonController {

    private final static Logger logger =LoggerFactory.getLogger(PersonController.class);
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

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

    @GetMapping(value = "/persons/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getPersonById(@PathVariable (value = "personId") Integer personId) {

        return personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("PersonId " + personId + "not found"));

    }

    @PostMapping(value = "/{addressId}/persons", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Person createPerson(@PathVariable (value = "addressId") Integer addressId,
                               @Valid @RequestBody Person person) {
        return addressRepository.findById(addressId).map(address -> {
            person.setAddress(address);
            return personRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + " not found"));
    }

    @PutMapping(value = "/{addressId}/persons/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person updatePerson(@PathVariable (value = "addressId") Integer addressId,
                               @PathVariable (value = "personId") Integer personId,
                               @Valid @RequestBody Person inputData) {
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

    @DeleteMapping("/{addressId}/persons/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable (value = "addressId") Integer addressId,
                                          @PathVariable (value = "personId") Integer personId) {
        return personRepository.findByIdAndAddressId(personId, addressId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + personId + " and addressId " + addressId));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
