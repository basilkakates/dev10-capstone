package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.User;

import java.util.List;

public interface UserRepository {
    User findById(int userId);

    List<User> findByName(String firstName, String lastName);

    User findByEmail(String email);

    List<User> findAll();

    User add(User user);

    boolean update(User user);
}
