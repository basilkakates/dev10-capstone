package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.Result;
import learn.capstone.clubrunner.domain.RunnerService;
import learn.capstone.clubrunner.models.Runner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/runner")
public class RunnerController {

    private final RunnerService service;

    public RunnerController(RunnerService service) {this.service = service;}

    @GetMapping
    public List<Runner> findAll() {return service.findAll();}

    @GetMapping("/{runnerId}")
    public Runner findById(@PathVariable int runnerId) {return service.findById(runnerId);}

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Runner runner) {
        Result<Runner> result = service.add(runner);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{runnerId}")
    public ResponseEntity<Void> deleteById(@PathVariable int runnerId) {
        if (service.deleteById(runnerId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

