package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.MemberMapper;
import learn.capstone.clubrunner.models.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MemberJdbcTemplateRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member findById(int memberId) {
        final String sql = "select member_id, user_id, club_id, isAdmin " +
                "from member where member_id = ?;";

        return jdbcTemplate.query(sql, new MemberMapper(), memberId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Member> findByUserId(int userId) {
        final String sql = "select member_id, user_id, club_id, isAdmin " +
                "from member where user_id = ?;";

        return jdbcTemplate.query(sql, new MemberMapper(), userId);
    }

    @Override
    public List<Member> findByClubId(int clubId) {
        final String sql = "select member_id, user_id, club_id, isAdmin " +
                "from member where club_id = ?;";

        return jdbcTemplate.query(sql, new MemberMapper(), clubId);
    }

    @Override
    public List<Member> findAdmins() {
        final String sql = "select member_id, user_id, club_id, isAdmin " +
                "from member where isAdmin = 1;";

        return jdbcTemplate.query(sql, new MemberMapper());
    }

    @Override
    public List<Member> findAll() {
        final String sql = "select member_id, user_id, club_id, isAdmin " +
                "from member;";

        return jdbcTemplate.query(sql, new MemberMapper());
    }

    @Override
    public Member add(Member member) {
        final String sql = "insert into member (user_id, club_id, isAdmin) " +
                "values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, member.getUser().getUser_id());
            ps.setInt(2, member.getClub().getClub_id());
            ps.setInt(3, member.getIsAdmin());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        member.setMember_id(keyHolder.getKey().intValue());
        return member;
    }

    @Override
    public boolean update(Member member) {
        final String sql = "update member set " +
                "isAdmin = ? " +
                "where member_id = ? " +
                "and user_id = ? " +
                "and club_id = ?;";

        return jdbcTemplate.update(sql,
                member.getIsAdmin(),
                member.getMember_id(),
                member.getUser().getUser_id(),
                member.getClub().getClub_id()) > 0;
    }

    @Override
    public boolean deleteById(int memberId) {
        return jdbcTemplate.update("delete from member where member_id = ?;", memberId) > 0;
    }
}
