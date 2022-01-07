package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunStatusRepository;
import learn.capstone.clubrunner.models.RunStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunStatusService {
    private final RunStatusRepository repository;

    public RunStatusService(RunStatusRepository repository) {
        this.repository = repository;
    }

    public Result<RunStatus> findById(int runStatusId) {
        Result<RunStatus> runStatusResult = new Result<>();
        runStatusResult.setPayload(repository.findById(runStatusId));

        if (runStatusResult.getPayload() == null) {
            String msg = String.format("runStatusId: %s, not found", runStatusId);
            runStatusResult.addMessage(msg, ResultType.NOT_FOUND);
        }

        return runStatusResult;
    }

    public Result<RunStatus> findByStatus(String status) {
        Result<RunStatus> runStatusResult = new Result<>();

        if (Validations.isNullOrBlank(status)) {
            runStatusResult.addMessage("runStatus cannot be null", ResultType.INVALID);
            return runStatusResult;
        }

        runStatusResult.setPayload(repository.findByStatus(status));

        if (runStatusResult.getPayload() == null) {
            String msg = String.format("status: %s, not found", status);
            runStatusResult.addMessage(msg, ResultType.NOT_FOUND);
        }

        return runStatusResult;
    }

    public List<RunStatus> findAll() {
        return repository.findAll();
    }

    public Result<RunStatus> add(RunStatus runStatus) {
        Result<RunStatus> runStatusResult = validate(runStatus);

        if (!runStatusResult.isSuccess()) {
            return runStatusResult;
        }

        if (runStatus.getRunStatusId() != 0) {
            runStatusResult.addMessage("run_status_id cannot be set for `add` operation", ResultType.INVALID);
            return runStatusResult;
        }

        if (repository.findByStatus(runStatus.getStatus()) != null) {
            runStatusResult.addMessage("duplicate status are not permitted", ResultType.INVALID);
            return runStatusResult;
        }

        runStatus = repository.add(runStatus);
        runStatusResult.setPayload(runStatus);
        return runStatusResult;
    }

    public Result<RunStatus> update(RunStatus runStatus) {
        Result<RunStatus> runStatusResult = validate(runStatus);

        if (!runStatusResult.isSuccess()) {
            return runStatusResult;
        }

        if (runStatus.getRunStatusId() <= 0) {
            runStatusResult.addMessage("run_status_id must be set for `update` operation", ResultType.INVALID);
            return runStatusResult;
        }

        RunStatus runStatusFindByStatus =  repository.findByStatus(runStatus.getStatus());

        if (runStatusFindByStatus != null && runStatusFindByStatus.getRunStatusId() != runStatus.getRunStatusId()) {
            runStatusResult.addMessage("duplicate status are not permitted", ResultType.INVALID);
            return runStatusResult;
        }

        if (!repository.update(runStatus)) {
            String msg = String.format("runStatusId: %s, not found", runStatus.getRunStatusId());
            runStatusResult.addMessage(msg, ResultType.NOT_FOUND);
        }

        return runStatusResult;
    }

    private Result<RunStatus> validate(RunStatus runStatus) {
        Result<RunStatus> runStatusResult = new Result<>();

        if (runStatus == null) {
            runStatusResult.addMessage("runStatus cannot be null", ResultType.INVALID);
            return runStatusResult;
        }

        if (Validations.isNullOrBlank(runStatus.getStatus())) {
            runStatusResult.addMessage("status is required", ResultType.INVALID);
            return runStatusResult;
        }

        return runStatusResult;
    }

}
