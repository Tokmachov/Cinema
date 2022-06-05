package edu.school21.cinema.config;

import edu.school21.cinema.repositories.UserAuthenticationRepository;
import edu.school21.cinema.repositories.UserRepository;
import edu.school21.cinema.services.AvatarService;
import edu.school21.cinema.services.UserAuthenticationService;
import edu.school21.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

@Configuration
@PropertySource("application.properties")
public class Config {
    @Value("${db.driver.class.name}")
    private String driverClassName;

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.user.name}")
    private String userName;

    @Value("${db.user.password}")
    private String userPassword;

    @Value("${images.storage.path}")
    private String avatarsPath;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(userPassword);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(jdbcTemplate());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository(), bCryptPasswordEncoder());
    }

    @Bean
    public UserAuthenticationRepository userAuthenticationRepository() {
        return new UserAuthenticationRepository(jdbcTemplate());
    }

    @Bean
    public UserAuthenticationService userAuthenticationService() {
        return new UserAuthenticationService(userAuthenticationRepository());
    }

    @Bean
    public AvatarService avatarService() throws IOException {
        return new AvatarService(avatarsPath);
    }
}
