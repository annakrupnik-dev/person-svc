package com.example.person.controller;

import com.example.person.domain.Address;
import com.example.person.domain.AddressDataWrapper;
import com.example.person.domain.Person;
import com.example.person.domain.PersonDataWrapper;
import com.example.person.exception.ResourceNotFoundException;
import com.example.person.repos.AddressRepository;
import com.example.person.repos.PersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final static Logger logger =LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressDataWrapper getAllAddresses() {
        Iterable<Address> addresses = addressRepository.findAll();
        AddressDataWrapper addressDataWrapper = new AddressDataWrapper();
        addressDataWrapper.setAddresses((List<Address>) addresses);
        return addressDataWrapper;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Address createAddress(@Valid @RequestBody Address address) {
        return addressRepository.save(address);
    }

    @PutMapping(value = "/{addressId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Address updateAddress(@PathVariable Integer addressId, @Valid @RequestBody Address inputData) {
        return addressRepository.findById(addressId).map(address -> {
            address.setCity(inputData.getCity());
            address.setState(inputData.getState());
            address.setStreet(inputData.getStreet());
            address.setZipcode(inputData.getZipcode());
            return addressRepository.save(address);
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + " not found"));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer addressId) {
        return addressRepository.findById(addressId).map(address -> {
            addressRepository.delete(address);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + " not found"));
    }


}
