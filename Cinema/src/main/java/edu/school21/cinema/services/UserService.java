package edu.school21.cinema.services;

import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(User user) {
        throwIfNotValid(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private static void throwIfNotValid(User user) {
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
