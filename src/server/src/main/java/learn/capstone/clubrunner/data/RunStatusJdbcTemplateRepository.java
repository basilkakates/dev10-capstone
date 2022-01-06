package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.RunStatusMapper;
import learn.capstone.clubrunner.models.RunStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RunStatusJdbcTemplateRepository implements RunStatusRepository {
    private final JdbcTemplate jdbcTemplate;

    public RunStatusJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RunStatus findById(int runStatusId) {
        final String sql = "select run_status_id, status " +
                "from run_status " +
                "where run_status_id = ?;";

        return jdbcTemplate.query(sql, new RunStatusMapper(), runStatusId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public RunStatus findByStatus(String status) {
        final String sql = "select run_status_id, status " +
                "from run_status " +
                "where status = ?;";

        return jdbcTemplate.query(sql, new RunStatusMapper(), status)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<RunStatus> findAll() {
        final String sql = "select run_status_id, status " +
                "from run_status;";
        return jdbcTemplate.query(sql, new RunStatusMapper());
    }

    @Override
    public RunStatus add(RunStatus runStatus) {
        final String sql = "insert into run_status (status) " +
                "values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, runStatus.getStatus());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        runStatus.setRunStatusId(keyHolder.getKey().intValue());
        return runStatus;
    }

    @Override
    public boolean update(RunStatus runStatus) {
        final String sql = "update run_status set " +
                "status = ? " +
                "where run_status_id = ?;";

        return jdbcTemplate.update(sql,
                runStatus.getStatus(),
                runStatus.getRunStatusId()) > 0;
    }
}
