package com.example.person.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonDataWrapper {

    private List<Person> persons = new ArrayList<>();
}
