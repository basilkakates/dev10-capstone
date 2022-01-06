package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.Runner;
import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunnerMapper implements RowMapper<Runner> {
    @Override
    public Runner mapRow(ResultSet resultSet, int i) throws SQLException {
        Runner runner = new Runner();
        runner.setRunner_id(resultSet.getInt("runner_id"));

        // User who is participating in the run
        User user = new User();
        user.setUser_id(resultSet.getInt("user_id"));

        runner.setUser(user);

        Run run = new Run();
        run.setRun_id(resultSet.getInt("run_id"));

        runner.setRun(run);

        return runner;
    }
}
