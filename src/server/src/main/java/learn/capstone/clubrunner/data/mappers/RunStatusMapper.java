package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.RunStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunStatusMapper implements RowMapper<RunStatus> {
    @Override
    public RunStatus mapRow(ResultSet resultSet, int i) throws SQLException {
        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(resultSet.getInt("run_status_id"));
        runStatus.setStatus(resultSet.getString("status"));

        return runStatus;
    }
}
