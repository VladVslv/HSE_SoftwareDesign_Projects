package org.example;


import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Data
public class Person {
    @Published
    public final String firstName;
    @Published
    public final String lastName;
    @Published
    public final LocalDate birthDate;

    int getAge(){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
