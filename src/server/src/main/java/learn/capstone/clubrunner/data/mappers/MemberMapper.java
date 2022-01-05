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
        member.setIsAdmin(resultSet.getInt("isAdmin"));

        // User who owns the membership
        UserMapper userMapper = new UserMapper();
        member.setUser(userMapper.mapRow(resultSet, i));

        ClubMapper clubMapper = new ClubMapper();
        member.setClub(clubMapper.mapRow(resultSet, i));

        return member;
    }
}
