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

    public RunJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Run> findAll() {
        final String sql = "select r.run_id, r.date, r.address, r.description run_description, " +
                "r.max_capacity, r.start_time, r.latitude, r.longitude, " +
                "u.user_id user_id_creator, u.first_name first_name_creator, u.last_name last_name_creator, u.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from run r " +
                "left join user u " +
                "on r.user_id = u.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id;";

        return jdbcTemplate.query(sql, new RunMapper());
    }

    @Override
    public Run findById(int runId) {
        final String sql = "select r.run_id, r.date, r.address, r.description run_description, " +
                "r.max_capacity, r.start_time, r.latitude, r.longitude, " +
                "u.user_id user_id_creator, u.first_name first_name_creator, u.last_name last_name_creator, u.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from run r " +
                "left join user u " +
                "on r.user_id = u.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id " +
                "where run_id = ?;";

        return jdbcTemplate.query(sql, new RunMapper(), runId).stream().findAny().orElse(null);
    }

    @Override
    public List<Run> findByUserId(int userId) {
        final String sql = "select r.run_id, r.date, r.address, r.description run_description, " +
                "r.max_capacity, r.start_time, r.latitude, r.longitude, " +
                "u.user_id user_id_creator, u.first_name first_name_creator, u.last_name last_name_creator, u.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from run r " +
                "left join user u " +
                "on r.user_id = u.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id " +
                "where r.user_id = ?;";

        return jdbcTemplate.query(sql, new RunMapper(), userId);
    }

    @Override
    public List<Run> findByClubId(int clubId) {
        final String sql = "select r.run_id, r.date, r.address, r.description run_description, " +
                "r.max_capacity, r.start_time, r.latitude, r.longitude, " +
                "u.user_id user_id_creator, u.first_name first_name_creator, u.last_name last_name_creator, u.email email_creator, " +
                "c.club_id, c.name, c.description club_description, " +
                "rs.run_status_id, rs.status " +
                "from run r " +
                "left join user u " +
                "on r.user_id = u.user_id " +
                "left join club c " +
                "on r.club_id = c.club_id " +
                "left join run_status rs " +
                "on r.run_status_id = rs.run_status_id " +
                "where r.club_id = ?;";

        return jdbcTemplate.query(sql, new RunMapper(), clubId);
    }

    @Override
    public Run add(Run run) {

        final String sql = "insert into run (date, address, description, club_id, user_id, max_capacity, start_time, " +
                "run_status_id, latitude, longitude) values (?,?,?,?,?,?,?,?,?,?);";

        KeyHolder keyholder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(run.getDate()));
            ps.setString(2, run.getAddress());
            ps.setString(3, run.getDescription());
            ps.setInt(4, run.getClub().getClubId());
            ps.setInt(5, run.getUser().getUserId());
            ps.setInt(6, run.getMaxCapacity());
            ps.setTime(7, Time.valueOf(run.getStartTime()));
            ps.setInt(8, run.getRunStatus().getRunStatusId());
            ps.setBigDecimal(9, run.getLatitude());
            ps.setBigDecimal(10, run.getLongitude());

            return ps;
        }, keyholder);

        if (rowsAffected <= 0) {
            return null;
        }

        run.setRunId(keyholder.getKey().intValue());
        return run;
    }

    @Override
    public boolean update(Run run) {

        final String sql = "update run set date = ?, address = ?, description = ?, max_capacity = ?, " +
                "start_time = ?, latitude = ?, longitude = ? where run_id = ?";

        return jdbcTemplate.update(sql, run.getDate(), run.getAddress(), run.getDescription(),
                run.getMaxCapacity(), run.getStartTime(), run.getLatitude(),
                run.getLongitude(), run.getRunId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int runId) {
        jdbcTemplate.update("delete from runner where run_id = ?", runId);
        return jdbcTemplate.update("delete from run where run_id = ?", runId) > 0;
    }
}
