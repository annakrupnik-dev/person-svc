package com.example.person.service;

import com.example.person.domain.Address;
import com.example.person.domain.AddressDataWrapper;
import com.example.person.exception.ResourceNotFoundException;
import com.example.person.repos.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    public AddressDataWrapper getAllAddresses() {
        Iterable<Address> addresses = addressRepository.findAll();
        AddressDataWrapper addressDataWrapper = new AddressDataWrapper();
        addressDataWrapper.setAddresses((List<Address>) addresses);
        return addressDataWrapper;
    }

    @Override
    public Address getAddressById(Integer addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + "not found"));
    }

    @Override
    public Address updateAddress(Integer addressId, Address inputData) {
        return addressRepository.findById(addressId).map(address -> {
            address.setCity(inputData.getCity());
            address.setState(inputData.getState());
            address.setStreet(inputData.getStreet());
            address.setZipcode(inputData.getZipcode());
            return addressRepository.save(address);
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + "not found"));

    }

    @Override
    public Address createAddress(Address inputData) {
        return addressRepository.save(inputData);
    }

    @Override
    public boolean deleteAddress(Integer addressId) {
        return addressRepository.findById(addressId).map(address -> {
            addressRepository.delete(address);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("AddressId " + addressId + " not found"));
    }

}
