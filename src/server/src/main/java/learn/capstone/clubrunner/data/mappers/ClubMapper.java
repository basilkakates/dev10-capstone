package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubMapper implements RowMapper<Club> {

    @Override
    public Club mapRow(ResultSet resultSet, int i) throws SQLException {

        Club club = new Club();
        club.setClub_id(resultSet.getInt("club_id"));
        club.setName(resultSet.getString("name"));
        club.setDescription(resultSet.getString("description"));

//        SecurityClearanceMapper securityClearanceMapper = new SecurityClearanceMapper();
//        agencyAgent.setSecurityClearance(securityClearanceMapper.mapRow(resultSet, i));

//          Will I need to add these as well? I think they are related to the lists.

//        AgentMapper agentMapper = new AgentMapper();
//        agencyAgent.setAgent(agentMapper.mapRow(resultSet, i));
        
        return club;
    }
}
