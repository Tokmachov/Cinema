package edu.school21.cinema.repositories;

import edu.school21.cinema.models.domain.UserAuthentication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

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
                userAuthentication.getLocalDateTime(),
                userAuthentication.getUser().getId(),
                userAuthentication.getAddress()
        );
    }

    public List<UserAuthentication> findByUserId(Long userId) {
        String query = "select * from cinema.user_authentications where user_id = ?";
        List<UserAuthentication> userAuthentications = jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
            String address = rs.getString("address");
            OffsetDateTime offsetDateTime = rs.getObject("date_and_time", OffsetDateTime.class);
            LocalDateTime localDateTime = offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            return new UserAuthentication(null, address, localDateTime);
        }, userId);
        return userAuthentications;
    }
}
