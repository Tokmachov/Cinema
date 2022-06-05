package edu.school21.cinema.repositories;

import edu.school21.cinema.models.domain.User;
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
                "first_name, last_name, phone_number, password, avatar_file_name)" +
                " values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                query,
                user.getName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getAvatarFileName());
        user.setId(findByPhoneNumber(user.getPhoneNumber()).get().getId());
    }

    public void update(User user) {
        String query = "update cinema.user" +
                " set first_name = ?," +
                " last_name = ?, " +
                " password = ?," +
                " avatar_file_name = ?" +
                " where id = ?";
        jdbcTemplate.update(query, user.getName(), user.getLastName(), user.getPassword(), user.getAvatarFileName(), user.getId());
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        String query = "select * from cinema.user where phone_number = ?";
        List<User> users = jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String password = rs.getString("password");
            String avatarFileName = rs.getString("avatar_file_name");
            return new User(id, name, lastname, phoneNumber, password, avatarFileName);
        }, phoneNumber);
        return !users.isEmpty() ? Optional.of(users.get(0)) : Optional.empty();
    }
}
