package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.RunnerMapper;
import learn.capstone.clubrunner.models.Runner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RunnerJdbcTemplateRepository implements RunnerRepository {

    private final JdbcTemplate jdbcTemplate;

    public RunnerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Runner> findAll() {
        final String sql = "select ru.runner_id, " +
                "u1.user_id, u1.first_name, u1.last_name, u1.email, " +
                "r.run_id, r.date, r.address, r.description run_description, r.max_capacity, " +
                "r.start_time, r.latitude, r.longitude, " +
                "u2.user_id user_id_creator, u2.first_name first_name_creator, u2.last_name last_name_creator, u2.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from runner ru " +
                "left join run r " +
                "on ru.run_id = r.run_id " +
                "left join user u1 " +
                "on ru.user_id = u1.user_id " +
                "left join user u2 " +
                "on r.user_id = u2.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id;";

        return jdbcTemplate.query(sql, new RunnerMapper());
    }

    @Override
    public Runner findById(int runnerId) {
        final String sql = "select ru.runner_id, " +
                "u1.user_id, u1.first_name, u1.last_name, u1.email, " +
                "r.run_id, r.date, r.address, r.description run_description, r.max_capacity, " +
                "r.start_time, r.latitude, r.longitude, " +
                "u2.user_id user_id_creator, u2.first_name first_name_creator, u2.last_name last_name_creator, u2.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from runner ru " +
                "left join run r " +
                "on ru.run_id = r.run_id " +
                "left join user u1 " +
                "on ru.user_id = u1.user_id " +
                "left join user u2 " +
                "on r.user_id = u2.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id " +
                "where ru.runner_id = ?;";

        return jdbcTemplate.query(sql, new RunnerMapper(), runnerId).stream().findAny().orElse(null);
    }

    @Override
    public List<Runner> findByUserId(int userId) {
        final String sql = "select ru.runner_id, " +
                "u1.user_id, u1.first_name, u1.last_name, u1.email, " +
                "r.run_id, r.date, r.address, r.description run_description, r.max_capacity, " +
                "r.start_time, r.latitude, r.longitude, " +
                "u2.user_id user_id_creator, u2.first_name first_name_creator, u2.last_name last_name_creator, u2.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from runner ru " +
                "left join run r " +
                "on ru.run_id = r.run_id " +
                "left join user u1 " +
                "on ru.user_id = u1.user_id " +
                "left join user u2 " +
                "on r.user_id = u2.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id " +
                "where ru.user_id = ?;";

        return jdbcTemplate.query(sql, new RunnerMapper(), userId);
    }

    @Override
    public List<Runner> findByRunId(int runId) {
        final String sql = "select ru.runner_id, " +
                "u1.user_id, u1.first_name, u1.last_name, u1.email, " +
                "r.run_id, r.date, r.address, r.description run_description, r.max_capacity, " +
                "r.start_time, r.latitude, r.longitude, " +
                "u2.user_id user_id_creator, u2.first_name first_name_creator, u2.last_name last_name_creator, u2.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from runner ru " +
                "left join run r " +
                "on ru.run_id = r.run_id " +
                "left join user u1 " +
                "on ru.user_id = u1.user_id " +
                "left join user u2 " +
                "on r.user_id = u2.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id " +
                "where ru.run_id = ?;";

        return jdbcTemplate.query(sql, new RunnerMapper(), runId);
    }

    @Override
    public Runner add(Runner runner) {

        final String sql = "insert into runner (run_id, user_id) values (?,?);";

        KeyHolder keyholder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, runner.getUser().getUserId());
            ps.setInt(2, runner.getRun().getRunId());

            return ps;
        }, keyholder);

        if (rowsAffected <= 0) {
            return null;
        }

        runner.setRunnerId(keyholder.getKey().intValue());
        return runner;
    }

    @Override
    @Transactional
    public boolean deleteById(int runnerId) {
        return jdbcTemplate.update("delete from runner where runner_id = ?", runnerId) > 0;
    }
}
