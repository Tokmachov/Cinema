package edu.school21.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String password;
}
