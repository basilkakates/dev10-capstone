package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet resultSet, int i) throws SQLException {

        Member member = new Member();
        member.setMember_id(resultSet.getInt("member_id"));
        member.setUser_id(resultSet.getInt("user_id"));
        member.setClub_id(resultSet.getInt("club_id"));
        member.setAdmin(resultSet.getInt("isAdmin"));

//        SecurityClearanceMapper securityClearanceMapper = new SecurityClearanceMapper();
//        agencyAgent.setSecurityClearance(securityClearanceMapper.mapRow(resultSet, i));

//          Will I need to add these as well? I think they are related to the lists.

//        AgentMapper agentMapper = new AgentMapper();
//        agencyAgent.setAgent(agentMapper.mapRow(resultSet, i));

        return member;
    }
}
