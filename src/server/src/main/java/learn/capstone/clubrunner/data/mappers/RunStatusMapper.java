package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.RunStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunStatusMapper implements RowMapper<RunStatus> {

    @Override
    public RunStatus mapRow(ResultSet resultSet, int i) throws SQLException {

        RunStatus runStatus = new RunStatus();
        runStatus.setRun_status_id(resultSet.getInt("run_status_id"));
        runStatus.setStatus(resultSet.getString("status"));

//        SecurityClearanceMapper securityClearanceMapper = new SecurityClearanceMapper();
//        agencyAgent.setSecurityClearance(securityClearanceMapper.mapRow(resultSet, i));

//          Will I need to add these as well? I think they are related to the lists.

//        AgentMapper agentMapper = new AgentMapper();
//        agencyAgent.setAgent(agentMapper.mapRow(resultSet, i));

        return runStatus;
    }
}
