package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunRepository;
import learn.capstone.clubrunner.models.Run;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RunService {

    private final RunRepository repository;

    public RunService(RunRepository repository) {
        this.repository = repository;
    }

    public List<Run> findAll() {
        List<Run> futureRuns = new ArrayList<>();
        for (Run run : repository.findAll()) {
            if (LocalDate.parse(run.getDate()).isAfter(LocalDate.now()) || LocalDate.parse(run.getDate()).isEqual(LocalDate.now())) {
                futureRuns.add(run);
            }
        }
        return futureRuns;
    }

    public Result<Run> findById(int runId) {
        Result<Run> runResult = new Result<>();
        runResult.setPayload(repository.findById(runId));

        if (runResult.getPayload() == null) {
            String msg = String.format("runId: %s, not found", runId);
            runResult.addMessage(msg, ResultType.NOT_FOUND);
        }

        return runResult;
    }

    public List<Run> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public List<Run> findByClubId(int clubId) {
        return repository.findByClubId(clubId);
    }

    public Result<Run> add(Run run) {

        Result<Run> result = validate(run);
        if (!result.isSuccess()) {
            return result;
        }

        if (run.getRunId() != 0) {
            result.addMessage("run id cannot be set for `add` operation", ResultType.INVALID);
        } else {
            result.setPayload(repository.add(run));
        }

        return result;
    }

    public Result<Run> update(Run run) {
        Result<Run> result = validate(run);
        if (!result.isSuccess()) {
            return result;
        }

        if (run.getRunId() <= 0) {
            result.addMessage("run id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(run)) {
            String msg = String.format("run id: %s, not found", run.getRunId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            //return result;
        }

        return result;
    }

    public boolean deleteById(int runId) {
        return repository.deleteById(runId);
    }

    private Result<Run> validate(Run run) {
        Result<Run> result = new Result<>();

        if (run == null) {
            result.addMessage("run cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(run.getAddress())) {
            result.addMessage("address is required", ResultType.INVALID);
        }

        if (run.getMaxCapacity() <= 0) {
            result.addMessage("max capacity cannot be zero or negative", ResultType.INVALID);
        }

        if (run.getLatitude() == null) {
            result.addMessage("latitude is required", ResultType.INVALID);
        }

        if (run.getLongitude() == null) {
            result.addMessage("longitude is required", ResultType.INVALID);
        }

        if (run.getDate() == null) {
            result.addMessage("date is required", ResultType.INVALID);
        }

        if (run.getStartTime() == null) {
            result.addMessage("start time is required", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        if (LocalDate.parse(run.getDate()).isBefore(LocalDate.now())) {
            result.addMessage("date cannot be in the past", ResultType.INVALID);
        } else if (LocalDate.parse(run.getDate()).isEqual(LocalDate.now()) && LocalTime.parse(run.getStartTime()).isBefore(LocalTime.now())) {
            result.addMessage("start time cannot be in the past", ResultType.INVALID);
        }

        return result;
    }
}

