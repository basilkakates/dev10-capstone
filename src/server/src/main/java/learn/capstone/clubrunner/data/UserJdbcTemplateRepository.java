package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.UserMapper;
import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(int userId) {
        final String sql = "select user_id, first_name, last_name, email, password " +
                "from user " +
                "where user_id = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<User> findByName(String firstName, String lastName) {
        final String sql = "select user_id, first_name, last_name, email, password " +
                "from user " +
                "where first_name = ? and last_name = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), firstName, lastName);
    }

    @Override
    public User findByEmail(String email) {
        final String sql = "select user_id, first_name, last_name, email, password " +
                "from user " +
                "where email = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), email).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        final String sql = "select user_id, first_name, last_name, email, password " +
                "from user;";

        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User add(User user) {
        final String sql = "insert into user (first_name, last_name, email, password) " +
                "values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update user set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "password = ? " +
                "where user_id = ?;";

        return jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserId()) > 0;
    }

    // No delete user, not in requirements, is a potentially very destructive operation.
}
