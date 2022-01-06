package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.data.mappers.ClubMapper;
import learn.capstone.clubrunner.models.Club;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ClubJdbcTemplateRepository implements ClubRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClubJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Club> findAll() {
        final String sql = "select club_id, name, description club_description from club;";
        return jdbcTemplate.query(sql, new ClubMapper());
    }

    @Override
    @Transactional
    public Club findById(int club_id) {

        final String sql = "select club_id, name, description club_description from club where club_id = ?;";

        Club result = jdbcTemplate.query(sql, new ClubMapper(), club_id).stream().findAny().orElse(null);

        return result;
    }
}
