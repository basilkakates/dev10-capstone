package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.Result;
import learn.capstone.clubrunner.domain.RunService;
import learn.capstone.clubrunner.models.Run;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/run")
public class RunController {

    private final RunService service;

    public RunController(RunService service) {
        this.service = service;
    }

    @GetMapping
    public List<Run> findAll() {
        return service.findAll();
    }

    @GetMapping("/{runId}")
    public ResponseEntity<Object> findById(@PathVariable int runId) {
        Result<Run> result = service.findById(runId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/user/{userId}")
    public List<Run> findByUserId(@PathVariable int userId) {
        return service.findByUserId(userId);
    }

    @GetMapping("/club/{clubId}")
    public List<Run> findByClubId(@PathVariable int clubId) {
        return service.findByClubId(clubId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Run run) {
        Result<Run> result = service.add(run);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{runId}")
    public ResponseEntity<Object> update(@PathVariable int runId, @RequestBody Run run) {
        if (runId != run.getRunId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Run> result = service.update(run);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{runId}")
    public ResponseEntity<Void> deleteById(@PathVariable int runId) {
        if (service.deleteById(runId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
