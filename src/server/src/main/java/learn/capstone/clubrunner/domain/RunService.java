package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunRepository;
import learn.capstone.clubrunner.models.Run;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunService {

    private final RunRepository repository;

    public RunService(RunRepository repository) {this.repository = repository;}

    public List<Run> findAll() {return repository.findAll();}

    public Run findById(int run_id) {return repository.findById(run_id);}

    public Result<Run> add(Run run) {

        Result<Run> result = validate(run);
        if (!result.isSuccess()) {
            return result;
        }

        if (run.getRunId() != 0) {
            result.addMessage("run id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        run = repository.add(run);
        result.setPayload(run);
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
            String msg= String.format("run id: %s, not found", run.getRunId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            //return result;
        }

        return result;
    }

    public boolean deleteById(int run_id) {
        return repository.deleteById(run_id);
    }

    private Result<Run> validate(Run run) {
        Result<Run> result = new Result<>();
        if (run == null) {
            result.addMessage("run cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(run.getAddress())) {
            result.addMessage("address is required", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(String.valueOf(run.getDate()))) {
            result.addMessage("date is required", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(String.valueOf(run.getMaxCapacity()))) {
            result.addMessage("max capacity is required", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(String.valueOf(run.getStartTime()))) {
            result.addMessage("start time is required", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(String.valueOf(run.getLatitude()))) {
            result.addMessage("latitude is required", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(String.valueOf(run.getLongitude()))) {
            result.addMessage("longitude is required", ResultType.INVALID);
            return result;
        }

        return result;
    }
}

