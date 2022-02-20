package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

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
}
