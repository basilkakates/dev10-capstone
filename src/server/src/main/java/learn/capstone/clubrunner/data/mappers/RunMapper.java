package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Run;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunMapper implements RowMapper<Run> {
    @Override
    public Run mapRow(ResultSet resultSet, int i) throws SQLException {
        Run run = new Run();
        run.setRunId(resultSet.getInt("run_id"));
        run.setDate(resultSet.getDate("date").toLocalDate());
        run.setAddress(resultSet.getString("address"));
        run.setDescription(resultSet.getString("run_description"));
        run.setMaxCapacity(resultSet.getInt("max_capacity"));
        run.setStartTime(resultSet.getTime("start_time").toLocalTime());
        run.setLatitude(resultSet.getBigDecimal("latitude"));
        run.setLongitude(resultSet.getBigDecimal("longitude"));

        ClubMapper clubMapper = new ClubMapper();
        run.setClub(clubMapper.mapRow(resultSet, i));

        // User who created the run
        RunCreatorMapper runCreatorMapper = new RunCreatorMapper();
        run.setUser(runCreatorMapper.mapRow(resultSet, i));

        RunStatusMapper runStatusMapper = new RunStatusMapper();
        run.setRunStatus(runStatusMapper.mapRow(resultSet, i));

        return run;
    }
}
