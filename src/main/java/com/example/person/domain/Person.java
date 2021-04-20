package com.example.person.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    @NotNull(message = "Name cannot be null")
    @Size(max = 100)
    private String name;

    @NotNull(message = "Age cannot be null")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Integer age;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    private Integer height;

    private Integer weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;
}
