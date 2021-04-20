package com.example.person.repos;

import com.example.person.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findByName (String name);
    Optional<Person> findByIdAndAddressId(Integer id, Integer addressId);
}
