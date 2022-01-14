package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunRepository;
import learn.capstone.clubrunner.data.RunnerRepository;
import learn.capstone.clubrunner.data.UserRepository;
import learn.capstone.clubrunner.models.Runner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;
    private final RunRepository runRepository;
    private final UserRepository userRepository;

    public RunnerService(RunnerRepository runnerRepository, RunRepository runRepository, UserRepository userRepository) {
        this.runnerRepository = runnerRepository;
        this.runRepository = runRepository;
        this.userRepository = userRepository;
    }

    public List<Runner> findAll() {
        return runnerRepository.findAll();
    }

    public List<Runner> findByUserId(int userId) {
        return runnerRepository.findByUserId(userId);
    }

    public List<Runner> findByRunId(int runId) {
        return runnerRepository.findByRunId(runId);
    }

    public Result<Runner> findById(int runnerId) {
        Result<Runner> result = new Result<>();
        result.setPayload(runnerRepository.findById(runnerId));

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
            result.setPayload(runnerRepository.add(runner));
        }

        return result;
    }

    public boolean deleteById(int runnerId) {
        return runnerRepository.deleteById(runnerId);
    }

    private Result<Runner> validate(Runner runner) {
        Result<Runner> result = new Result<>();
        if (runner == null) {
            result.addMessage("runner cannot be null", ResultType.INVALID);
            return result;
        }

        if (runner.getRun() == null) {
            result.addMessage("run is required", ResultType.INVALID);
        } else if (runRepository.findById(runner.getRun().getRunId()) == null) {
            String msg = String.format("runId %s not found", runner.getRun().getRunId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        if (runner.getUser() == null) {
            result.addMessage("user is required", ResultType.INVALID);
        } else if (userRepository.findById(runner.getUser().getUserId()) == null) {
            String msg = String.format("userId %s not found", runner.getUser().getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }
}
