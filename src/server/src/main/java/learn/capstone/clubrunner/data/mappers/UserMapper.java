package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();
        user.setUser_id(resultSet.getInt("user_id"));
        user.setFirst_name(resultSet.getString("first_name"));
        user.setLast_name(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));

//        SecurityClearanceMapper securityClearanceMapper = new SecurityClearanceMapper();
//        agencyAgent.setSecurityClearance(securityClearanceMapper.mapRow(resultSet, i));

//          Will I need to add these as well? I think they are related to the lists.

//        AgentMapper agentMapper = new AgentMapper();
//        agencyAgent.setAgent(agentMapper.mapRow(resultSet, i));

        return user;
    }
}
