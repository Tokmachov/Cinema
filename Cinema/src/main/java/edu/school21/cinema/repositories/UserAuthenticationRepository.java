package edu.school21.cinema.repositories;

import edu.school21.cinema.models.UserAuthentication;
import edu.school21.cinema.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class UserAuthenticationRepository implements Repository<UserAuthentication> {
    private JdbcTemplate jdbcTemplate;

    public UserAuthenticationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(UserAuthentication userAuthentication) {
        final String query = "insert into cinema.user_authentications (" +
                "date_and_time, user_id, address)" +
                " values (?, ?, ?)";
        jdbcTemplate.update(
                query,
                userAuthentication.getLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                userAuthentication.getUser().getId(),
                userAuthentication.getAddress()
        );
    }

}
