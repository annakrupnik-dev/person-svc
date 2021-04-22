package com.example.person.service;

import com.example.person.domain.Address;
import com.example.person.exception.ResourceNotFoundException;
import com.example.person.repos.AddressRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressServiceImpl addressService;
    @Test
    public void whenSaveAddress_shouldReturnAddress() {
        Address address = new Address();
        address.setState("Test State");
        address.setZipcode(90909090);
        address.setCity("Test City");
        address.setStreet("Test Street");

        when(addressRepository.save(ArgumentMatchers.any(Address.class))).thenReturn(address);
        Address created = addressService.createAddress(address);
        Assert.assertEquals(created.getZipcode(), address.getZipcode());
        verify(addressRepository).save(address);
    }

    @Test
    public void shouldReturnAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        given(addressRepository.findAll()).willReturn(addresses);
        List<Address> expected = addressService.getAllAddresses().getAddresses();
        Assert.assertEquals(expected, addresses);
        verify(addressRepository).findAll();
    }

    @Test
    public void whenGivenId_shouldReturnAddress_ifFound(){
        Address address = new Address();
        address.setId(100);
        address.setState("Test State");
        address.setZipcode(90909090);
        address.setCity("Test City");
        address.setStreet("Test Street");
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        addressService.getAddressById(address.getId());
        verify(addressRepository).findById(address.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_exception_when_deleteAddress_doesnt_exist() {
        Address address = new Address();
        address.setId(100);
        address.setState("Test State");
        address.setZipcode(90909090);
        address.setCity("Test City");
        address.setStreet("Test Street");

        given(addressRepository.findById(anyInt())).willReturn(Optional.ofNullable(null));
        addressService.deleteAddress(address.getId());
    }

    @Test
    public void whenGivenId_shouldDeleteAddress_ifFound(){
        Address address = new Address();
        address.setId(100);
        address.setState("Test State");
        address.setZipcode(90909090);
        address.setCity("Test City");
        address.setStreet("Test Street");
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        addressService.deleteAddress(address.getId());
        verify(addressRepository).delete(address);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_exception_when_updateAddress_doesnt_exist() {
        Address address = new Address();
        address.setId(100);
        address.setState("Test State");
        address.setZipcode(90909090);
        address.setCity("Test City");
        address.setStreet("Test Street");

        Address newAddress = new Address();
        newAddress.setId(200);
        newAddress.setState("New Test State");
        newAddress.setZipcode(707070);
        newAddress.setCity("New Test City");
        newAddress.setStreet("New Test Street");

        given(addressRepository.findById(anyInt())).willReturn(Optional.ofNullable(null));
        addressService.updateAddress(address.getId(), newAddress);
    }
}
