package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunnerRepository;
import learn.capstone.clubrunner.models.Runner;
import learn.capstone.clubrunner.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunnerService {

    private final RunnerRepository repository;

    public RunnerService(RunnerRepository repository) {
        this.repository = repository;
    }

    public List<Runner> findAll() {
        return repository.findAll();
    }

    public Result<Runner> findById(int runnerId) {
        Result<Runner> result = new Result<>();
        result.setPayload(repository.findById(runnerId));

        if (result.getPayload() == null) {
            String msg = String.format("runnerId: %s not found", runnerId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Runner> add(Runner runner) {

        Result<Runner> result = validate(runner);
        if (!result.isSuccess()) {
            return result;
        }

        if (runner.getRunnerId() != 0) {
            result.addMessage("runner id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        } else {
            result.setPayload(repository.add(runner));
        }

        return result;
    }

    public boolean deleteById(int runnerId) {
        return repository.deleteById(runnerId);
    }

    private Result<Runner> validate(Runner runner) {
        Result<Runner> result = new Result<>();
        if (runner == null) {
            result.addMessage("runner cannot be null", ResultType.INVALID);
            return result;
        }

        if (runner.getRun().getRunId() <= 0) {
            result.addMessage("run Id cannot be 0", ResultType.INVALID);
            return result;
        }

        if (runner.getUser().getUserId() <= 0) {
            result.addMessage("user Id cannot be null", ResultType.INVALID);
            return result;
        }

        return result;
    }
}