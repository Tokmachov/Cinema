package edu.school21.cinema.models.dto;

import edu.school21.cinema.models.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationDto {
    private String address;
    private String date;
    private String time;
}
