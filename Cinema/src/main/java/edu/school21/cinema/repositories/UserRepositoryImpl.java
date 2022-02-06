package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements Repository<User> {
    private static List<User> users = new ArrayList<>();
    @Override
    public void save(User item) {
        users.add(item);
    }

    public Optional<User> findByPhone(String phone) {
        return users.stream().filter(u -> u.getPhoneNumber().equals(phone)).findFirst();
    }
}
