package edu.school21.cinema.services;

import edu.school21.cinema.models.domain.UserAuthentication;
import edu.school21.cinema.models.dto.AuthenticationDto;

import java.time.format.DateTimeFormatter;

public class UserAuthenticationMapper {
    public static AuthenticationDto toDto(UserAuthentication userAuthentication) {
        String address = userAuthentication.getAddress();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");

        String time = userAuthentication.getLocalDateTime().toLocalTime().format(timeFormatter);
        String date = userAuthentication.getLocalDateTime().toLocalDate().format(dateFormatter);
        return new AuthenticationDto(address, date, time);
    }
}
