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

    public RunnerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Runner> findAll() {
        final String sql = "select runner_id, run_id, user_id from runner;";
        return jdbcTemplate.query(sql, new RunnerMapper());
    }

    @Override
    @Transactional
    public Runner findById(int runner_id) {

        final String sql = "select runner_id, run_id, user_id from runner where runner_id = ?;";

        Runner result = jdbcTemplate.query(sql, new RunnerMapper(), runner_id).stream().findAny().orElse(null);

        return result;
    }

    @Override
    public Runner add(Runner runner) {

        final String sql = "insert into runner (run_id, user_id) values (?,?);";

        KeyHolder keyholder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            return ps;
        }, keyholder);

        if (rowsAffected <= 0 ) {
            return null;
        }

        runner.setRunner_id(keyholder.getKey().intValue());
        return runner;
    }

    @Override
    @Transactional
    public boolean deleteById(int runner_id) {
        return jdbcTemplate.update("delete from runner where run_id = ?", runner_id) > 0;
    }
}