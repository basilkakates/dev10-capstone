package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.Result;
import learn.capstone.clubrunner.domain.UserService;
import learn.capstone.clubrunner.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findById(@PathVariable int userId) {
        Result<User> result = service.findById(userId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/name")
    public ResponseEntity<Object> findByName(@RequestBody String[] name) {
        Result<List<User>> result = service.findByName(name[0], name[1]);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/email")
    public ResponseEntity<Object> findByEmail(@RequestBody String email) {
        Result<User> result = service.findByEmail(email);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    public List<User> findUsersByRunId(@PathVariable int runId) {
        return service.findUsersByRunId(runId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody User user) {
        Result<User> result = service.add(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable int userId, @RequestBody User user) {
        if (userId != user.getUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<User> result = service.update(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }
}
