package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunnerRepository;
import learn.capstone.clubrunner.models.Runner;
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

    public Runner findById(int runnerId) {
        return repository.findById(runnerId);
    }

    public Result<Runner> add(Runner runner) {

        Result<Runner> result = validate(runner);
        if (!result.isSuccess()) {
            return result;
        }

        if (runner.getRunnerId() != 0) {
            result.addMessage("runner id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        runner = repository.add(runner);
        result.setPayload(runner);
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

        return result;
    }
}
