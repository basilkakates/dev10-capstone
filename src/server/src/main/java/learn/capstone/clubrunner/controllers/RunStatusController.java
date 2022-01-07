package learn.capstone.clubrunner.controllers;

import learn.capstone.clubrunner.domain.Result;
import learn.capstone.clubrunner.domain.RunStatusService;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.RunStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/runStatus")

public class RunStatusController {

    @GetMapping
    public List<RunStatus> findAll() {return service.findAll();}

    @GetMapping("/{runStatusId}")
    public RunStatus findById(@PathVariable int runStatusId) {return service.findById(runStatusId);}

    @GetMapping("/{status}")
    public RunStatus findByStatus(@PathVariable String status) {return service.findByStatus(status);}

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
