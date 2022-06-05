package edu.school21.cinema.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String avatarFileName;
}
