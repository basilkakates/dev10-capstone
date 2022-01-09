package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Member;
import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet resultSet, int i) throws SQLException {

        Member member = new Member();
        member.setMemberId(resultSet.getInt("member_id"));
        member.setIsAdmin(resultSet.getInt("isAdmin"));

        // User who owns the membership
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));

        member.setUser(user);

        Club club = new Club();
        club.setClubId(resultSet.getInt("club_id"));

        member.setClub(club);

        return member;
    }
}
