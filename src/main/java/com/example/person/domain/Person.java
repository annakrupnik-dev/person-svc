package com.example.person.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
public class Person {

    public Person(String name, Integer age, String gender, Integer height, Integer weight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    private Integer height;

    private Integer weight;


}
