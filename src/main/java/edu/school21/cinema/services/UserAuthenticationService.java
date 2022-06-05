package edu.school21.cinema.services;

import edu.school21.cinema.models.domain.UserAuthentication;
import edu.school21.cinema.repositories.UserAuthenticationRepository;

import java.util.List;

public class UserAuthenticationService {
    private UserAuthenticationRepository userAuthenticationRepository;

    public UserAuthenticationService(UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
    }

    public void save(UserAuthentication userAuthentication) {
        userAuthenticationRepository.save(userAuthentication);
    }

    public List<UserAuthentication> findByUserId(Long userId) {
        return userAuthenticationRepository.findByUserId(userId);
    }
}
