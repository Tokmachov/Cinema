package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User> {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        final String query = "insert into cinema.user (" +
                "first_name, last_name, phone_number, password)" +
                " values (?, ?, ?, ?)";
        jdbcTemplate.update(
                query,
                user.getName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getPassword());
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        String query = "select * from cinema.user where phone_number = ?";
        List<User> users = jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
            String name = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String password = rs.getString("password");
            return new User(name, lastname, phoneNumber, password);
        }, phoneNumber);
        return !users.isEmpty() ? Optional.of(users.get(0)) : Optional.empty();
    }
}
