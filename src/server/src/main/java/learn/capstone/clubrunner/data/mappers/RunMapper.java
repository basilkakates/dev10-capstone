package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Run;
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
        run.setClub_id(resultSet.getInt("club_id"));
        run.setUser_id(resultSet.getInt("user_id"));
        run.setMax_capacity(resultSet.getInt("max_capacity"));
        run.setStart_time(resultSet.getTime("start_time").toLocalTime());
        run.setRun_status_id(resultSet.getInt("run_status_id"));
        run.setLatitude(resultSet.getBigDecimal("latitude"));
        run.setLongitude(resultSet.getBigDecimal("longitude"));

        return run;
    }
}
