package edu.school21.cinema.services;

import edu.school21.cinema.models.UserAuthentication;
import edu.school21.cinema.repositories.UserAuthenticationRepository;

public class UserAuthenticationService {
    private UserAuthenticationRepository userAuthenticationRepository;

    public UserAuthenticationService(UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
    }

    public void save(UserAuthentication userAuthentication) {
        userAuthenticationRepository.save(userAuthentication);
    }
}
