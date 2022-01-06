package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.MemberMapper;
import learn.capstone.clubrunner.data.mappers.RunMapper;
import learn.capstone.clubrunner.data.mappers.RunnerMapper;
import learn.capstone.clubrunner.data.mappers.UserMapper;
import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public User findById(int userId) {
        final String sql = "select user_id, first_name, last_name, email, password " +
                "from user " +
                "where user_id = ?;";

        User user = jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findFirst().orElse(null);

        if (user != null) {
            buildUser(user);
        }

        return user;
    }

    @Override
    public List<User> findByName(String firstName, String lastName) {
        final String sql = "select user_id, first_name, last_name, email, password " +
                "from user " +
                "where first_name = ? and last_name = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), firstName, lastName);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        final String sql = "select user_id, first_name, last_name, email, password " +
                "from user " +
                "where email = ?;";

        User user = jdbcTemplate.query(sql, new UserMapper(), email).stream()
                .findFirst().orElse(null);

        if (user != null) {
            buildUser(user);
        }

        return user;
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
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getLast_name());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUser_id(keyHolder.getKey().intValue());
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
                user.getFirst_name(),
                user.getLast_name(),
                user.getEmail(),
                user.getPassword(),
                user.getUser_id()) > 0;
    }

    // No delete user, not in requirements, is a potentially very destructive operation.

    private void buildUser(User user) {
        addRunsParticipating(user);
        addMemberships(user);
        addRunsCreated(user);
    }

    private void addRunsParticipating(User user) {
        final String sql = "select r.run_id, r.date, r.address, r.description run_description, " +
                "r.max_capacity, r.start_time, r.latitude, r.longitude, ru.runner_id " +
                "from run r" +
                "inner join runner ru on r.run_id = ru.runner_id " +
                "where ru.user_id = ?;";

        var runsParticipating = jdbcTemplate.query(sql, new RunnerMapper(), user.getUser_id());

        user.setRunsParticipating(runsParticipating);
    }

    private void addMemberships(User user) {
        final String sql = "select c.club_id, c.name, c.description club_description, m.member_id, m.isAdmin " +
                "from club c " +
                "inner join member m on c.club_id = m.club_id " +
                "where m.user_id = ?;";

        var memberships = jdbcTemplate.query(sql, new MemberMapper(), user.getUser_id());

        user.setMemberships(memberships);
    }

    private void addRunsCreated(User user) {
        final String sql = "select run_id, date, address, description run_description," +
                "max_capacity, start_time, latitude, longitude " +
                "from run where user_id = ?;";

        var runsCreated = jdbcTemplate.query(sql, new RunMapper(), user.getUser_id());

        user.setRunsCreated(runsCreated);
    }
}
