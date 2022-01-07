package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.UserRepository;
import learn.capstone.clubrunner.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Result<User> findById(int userId) {
        Result<User> result = new Result<>();
        result.setPayload(repository.findById(userId));

        if (result.getPayload() == null) {
            String msg = String.format("userId: %s not found", userId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<List<User>> findByName(String firstName, String lastName) {
        Result<List<User>> result = new Result<>();

        if (Validations.isNullOrBlank(firstName) || Validations.isNullOrBlank(lastName)) {
            result.addMessage("firstName and lastName are required", ResultType.INVALID);
        } else {
            result.setPayload(repository.findByName(firstName, lastName));
        }

        if (result.isSuccess() && result.getPayload().size() <= 0) {
            String msg = String.format("%s %s not found", firstName, lastName);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<User> findByEmail(String email) {
        Result<User> result = new Result<>();

        if (Validations.isNullOrBlank(email)) {
            result.addMessage("email is required", ResultType.INVALID);
            return result;
        } else {
            result.setPayload(repository.findByEmail(email));
        }

        if (result.getPayload() == null) {
            String msg = String.format("email: %s not found", email);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("userId cannot be set for `add` operation", ResultType.INVALID);
        } else if (repository.findByEmail(user.getEmail()) != null) {
            result.addMessage("duplicate emails are not permitted", ResultType.INVALID);
        } else {
            result.setPayload(repository.add(user));
        }

        return result;
    }

    public Result<User> update(User user) {
        Result<User> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addMessage("userID must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        User userFindByEmail = repository.findByEmail(user.getEmail());

        if (userFindByEmail != null && userFindByEmail.getUserId() != user.getUserId()) {
            result.addMessage("duplicate emails are not permitted", ResultType.INVALID);
            return result;
        }

        if (!repository.update(user)) {
            String msg = String.format("userId: %s not found", user.getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();

        if (user == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(user.getFirstName())) {
            result.addMessage("firstName is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getLastName())) {
            result.addMessage("lastName is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("email is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getPassword())) {
            result.addMessage("password is required", ResultType.INVALID);
        }

        return result;
    }
}
