package edu.school21.cinema.services;

import edu.school21.cinema.models.User;

public class UserValidatorService {
    public static void throwIfNotValid(User user) {
        if (user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name must not be empty");
        }
        if (user.getLastName().isEmpty()) {
            throw new IllegalArgumentException("User last name must not be empty");
        }
        if (!user.getPhoneNumber().matches("\\+{0,1}\\d+")) {
            throw new IllegalArgumentException("Incorrect phone number.");
        }
        if (user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User password must not be empty");
        }
    }
}
