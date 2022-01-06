package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.RunStatus;
import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunMapper implements RowMapper<Run> {
    @Override
    public Run mapRow(ResultSet resultSet, int i) throws SQLException {
        Run run = new Run();
        run.setRun_id(resultSet.getInt("run_id"));
        run.setDate(resultSet.getDate("date").toLocalDate());
        run.setAddress(resultSet.getString("address"));
        run.setDescription(resultSet.getString("run_description"));
        run.setMax_capacity(resultSet.getInt("max_capacity"));
        run.setStart_time(resultSet.getTime("start_time").toLocalTime());
        run.setLatitude(resultSet.getBigDecimal("latitude"));
        run.setLongitude(resultSet.getBigDecimal("longitude"));

        Club club = new Club();
        club.setClub_id(resultSet.getInt("club_id"));

        run.setClub(club);

        // User who created the run
        User user = new User();
        user.setUser_id(resultSet.getInt("user_id"));

        run.setUser(user);

        RunStatus runStatus = new RunStatus();
        runStatus.setRun_status_id(resultSet.getInt("run_status_id"));

        run.setRunStatus(runStatus);

        return run;
    }
}
