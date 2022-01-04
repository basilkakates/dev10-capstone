package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Runner;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunnerMapper implements RowMapper<Runner> {

    @Override
    public Runner mapRow(ResultSet resultSet, int i) throws SQLException {

        Runner runner = new Runner();
        runner.setRunner_id(resultSet.getInt("runner_id"));
        runner.setRun_id(resultSet.getInt("run_id"));
        runner.setUser_id(resultSet.getInt("user_id"));

//        SecurityClearanceMapper securityClearanceMapper = new SecurityClearanceMapper();
//        agencyAgent.setSecurityClearance(securityClearanceMapper.mapRow(resultSet, i));

//          Since these is a bridge table will I need to add these as well? I think they are related to the lists. Runner does not have lists however

//        AgentMapper agentMapper = new AgentMapper();
//        agencyAgent.setAgent(agentMapper.mapRow(resultSet, i));

        return runner;
    }
}
