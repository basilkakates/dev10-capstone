package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.ClubService;
import learn.capstone.clubrunner.models.Club;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/club")
public class ClubController {

    private final ClubService service;

    public ClubController(ClubService service) {this.service = service;}

    @GetMapping
    public List<Club> findAll() {return service.findAll();}

    @GetMapping("/{clubId}")
    public Club findById(@PathVariable int clubId) {return service.findById(clubId);}
}