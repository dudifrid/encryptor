package david.encryptor;
//package com.ociweb.jnb.lombok;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(exclude={"address","city","state","zip"})
public class Person {
    enum Gender { Male, Female }

    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private final Gender gender;
    @NonNull private final Date dateOfBirth;
    
    private String ssn;
    private String address;
    private String city;
    private String state;
    private String zip;
}