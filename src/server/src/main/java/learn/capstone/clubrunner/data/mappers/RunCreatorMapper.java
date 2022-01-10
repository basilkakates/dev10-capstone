package learn.capstone.clubrunner.data.mappers;

import learn.capstone.clubrunner.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunCreatorMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id_creator"));
        user.setFirstName(resultSet.getString("first_name_creator"));
        user.setLastName(resultSet.getString("last_name_creator"));
        user.setEmail(resultSet.getString("email_creator"));

        return user;
    }
}
