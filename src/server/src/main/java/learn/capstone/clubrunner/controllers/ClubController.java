package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.ClubService;
import learn.capstone.clubrunner.domain.Result;
import learn.capstone.clubrunner.models.Club;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/club")
public class ClubController {

    private final ClubService service;

    public ClubController(ClubService service) {
        this.service = service;
    }

    @GetMapping
    public List<Club> findAll() {
        return service.findAll();
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<Object> findById(@PathVariable int clubId) {
        Result<Club> result = service.findById(clubId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/admin/{userId}")
    public ResponseEntity<Object> findAdminForClubByUserId(@PathVariable int userId) {
        Result<Club> result = service.findAdminForClubByUserId(userId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/user/{userId}")
    public List<Club> findClubsByUserId(@PathVariable int userId) {
        return service.findClubsByUserId(userId);
    }
}