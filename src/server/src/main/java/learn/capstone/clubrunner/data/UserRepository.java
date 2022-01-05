package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository {
    @Transactional
    User findById(int userId);

    List<User> findByName(String firstName, String lastName);

    @Transactional
    User findByEmail(String email);

    List<User> findAll();

    User add(User user);

    boolean update(User user);
}
