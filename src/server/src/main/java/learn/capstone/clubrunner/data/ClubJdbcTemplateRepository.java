package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.ClubMapper;
import learn.capstone.clubrunner.models.Club;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClubJdbcTemplateRepository implements ClubRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClubJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Club> findAll() {
        final String sql = "select club_id, name, description club_description from club;";
        return jdbcTemplate.query(sql, new ClubMapper());
    }

    @Override
    public Club findById(int clubId) {

        final String sql = "select club_id, name, description club_description from club where club_id = ?;";

        return jdbcTemplate.query(sql, new ClubMapper(), clubId).stream().findAny().orElse(null);
    }

    @Override
    public Club findAdminForClubByUserId(int userId) {
        final String sql = "select c.club_id, c.name, c.description club_description " +
                "from club c " +
                "inner join member m " +
                "on c.club_id = m.club_id " +
                "where m.isAdmin = 1 " +
                "and m.user_id = ?;";

        return jdbcTemplate.query(sql, new ClubMapper(), userId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Club> findClubsByUserId(int userId) {
        final String sql = "select c.club_id, c.name, c.description club_description " +
                "from club c " +
                "inner join member m " +
                "on c.club_id = m.club_id " +
                "where m.user_id = ?;";

        return jdbcTemplate.query(sql, new ClubMapper(), userId);
    }
}
