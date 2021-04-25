package com.example.person.controller;

import com.example.person.ErrorResponse;
import com.example.person.domain.Person;
import com.example.person.domain.PersonDataWrapper;
import com.example.person.exception.ResourceNotFoundException;
import com.example.person.service.PersonService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/addresses")
public class PersonController {

    private PersonService personService;

    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDataWrapper getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/persons/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getPersonById(@PathVariable (value = "personId") Integer personId) {
        return personService.getPersonById(personId);
    }

    @PostMapping(value = "/{addressId}/persons", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Person createPerson(@PathVariable (value = "addressId") Integer addressId,
                               @Valid @RequestBody Person person) {
        return personService.createPerson(addressId,person);
    }

    @PutMapping(value = "/{addressId}/persons/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person updatePerson(@PathVariable (value = "addressId") Integer addressId,
                               @PathVariable (value = "personId") Integer personId,
                               @Valid @RequestBody Person inputData) {
        return personService.updatePerson(addressId,personId,inputData);
    }

    @DeleteMapping("/{addressId}/persons/{personId}")
    public boolean deletePerson(@PathVariable (value = "addressId") Integer addressId,
                                @PathVariable (value = "personId") Integer personId) {
        return personService.deletePerson(addressId,personId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Resource not found", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Internal Server Error", details);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
