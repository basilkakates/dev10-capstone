package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Runner;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunnerMapper implements RowMapper<Runner> {
    @Override
    public Runner mapRow(ResultSet resultSet, int i) throws SQLException {
        Runner runner = new Runner();
        runner.setRunnerId(resultSet.getInt("runner_id"));

        // User who is participating in the run
        UserMapper userMapper = new UserMapper();
        runner.setUser(userMapper.mapRow(resultSet, i));

        RunMapper runMapper = new RunMapper();
        runner.setRun(runMapper.mapRow(resultSet, i));

        return runner;
    }
}
