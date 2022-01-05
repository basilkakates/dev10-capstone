package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.RunMapper;
import learn.capstone.clubrunner.models.Run;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;

@Repository
public class RunJdbcTemplateRepository implements RunRepository {

    private final JdbcTemplate jdbcTemplate;

    public RunJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Run> findAll() {
        final String sql = "select run_id, date, address, club_id, user_id, max_capacity, start_time, run_status_id, latitude, longitude from run;";
        return jdbcTemplate.query(sql, new RunMapper());
    }

    @Override
    @Transactional
    public Run findById(int run_id) {

        final String sql = "select run_id, date, address, club_id, user_id, max_capacity, start_time, run_status_id, latitude, longitude from run where run_id = ?;";

        Run result = jdbcTemplate.query(sql, new RunMapper(), run_id).stream().findAny().orElse(null);

        if (result != null) {
            add(result);
        }

        return result;
    }

    @Override
    public Run add(Run run) {

        final String sql = "insert into run (date, address, club_id, user_id, max_capacity, start_time, run_status_id, latitude, longitude) values (?,?,?,?,?,?,?,?,?);";

        KeyHolder keyholder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(run.getDate()));
            ps.setString(2, run.getAddress());
            ps.setInt(3, run.getClub_id());
            ps.setInt(4, run.getUser_id());
            ps.setInt(5, run.getMax_capacity());
            ps.setTime(6, Time.valueOf(run.getStart_time()));
            ps.setInt(7, run.getRun_status_id());
            ps.setBigDecimal(8, run.getLatitude());
            ps.setBigDecimal(9, run.getLongitude());

            return ps;
        }, keyholder);

        if (rowsAffected <= 0 ) {
            return null;
        }

        run.setRun_id(keyholder.getKey().intValue());
        return run;
    }

    @Override
    public boolean update(Run run) {

        final String sql = "update run set date = ?, address = ?, club_id = ?, user_id = ?, max_capacity = ?, start_time = ?, run_status_id = ?, latitude = ?, longitude = ? where run_id = ?";

        return jdbcTemplate.update(sql, run.getDate(), run.getAddress(), run.getClub_id(), run.getUser_id(),
                run.getMax_capacity(), run.getStart_time(), run.getRun_status_id(), run.getLatitude(),
                run.getLongitude(), run.getRun_id()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int run_id) {
        return jdbcTemplate.update("delete from run where run_id = ?", run_id) > 0;
    }
}
