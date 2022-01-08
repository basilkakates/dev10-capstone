package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.Club;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubMapper implements RowMapper<Club> {
    @Override
    public Club mapRow(ResultSet resultSet, int i) throws SQLException {

        Club club = new Club();
        club.setClubId(resultSet.getInt("club_id"));
        club.setName(resultSet.getString("name"));
        club.setDescription(resultSet.getString("club_description"));

        return club;
    }
}
