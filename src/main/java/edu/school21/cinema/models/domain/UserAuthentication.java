package edu.school21.cinema.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class UserAuthentication {
    private User user;
    private String address;
    private LocalDateTime localDateTime;
}
