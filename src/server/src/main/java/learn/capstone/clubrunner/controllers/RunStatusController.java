package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.Result;
import learn.capstone.clubrunner.domain.RunStatusService;
import learn.capstone.clubrunner.models.RunStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/runStatus")

public class RunStatusController {

    private final RunStatusService service;

    public RunStatusController(RunStatusService service) {this.service = service;}

    @GetMapping
    public List<RunStatus> findAll() {return service.findAll();}

    @GetMapping("/{runStatusId}")
    public ResponseEntity<Object> findById(@PathVariable int runStatusId) {
        Result<RunStatus> result =  service.findById(runStatusId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> findByStatus(@PathVariable String status) {
        Result<RunStatus> result = service.findByStatus(status);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody RunStatus runStatus) {
        Result<RunStatus> result = service.add(runStatus);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{runId}")
    public ResponseEntity<Object> update(@PathVariable int runStatusId, @RequestBody RunStatus runStatus) {
        if (runStatusId != runStatus.getRunStatusId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<RunStatus> result = service.update(runStatus);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }
}
