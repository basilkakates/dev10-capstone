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

}
