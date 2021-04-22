package com.example.person.service;

import com.example.person.domain.Address;
import com.example.person.domain.AddressDataWrapper;
import com.example.person.domain.Person;
import org.springframework.web.bind.annotation.PathVariable;

public interface AddressService {

    AddressDataWrapper getAllAddresses();
    Address getAddressById(Integer personId);
    Address updateAddress(Integer addressId, Address inputData);
    Address createAddress(Address inputData);
    boolean deleteAddress(Integer addressId);

}
