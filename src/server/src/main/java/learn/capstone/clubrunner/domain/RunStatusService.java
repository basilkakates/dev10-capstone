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
        Result<RunStatus> result = new Result<>();
        result.setPayload(repository.findById(runStatusId));

        if (result.getPayload() == null) {
            String msg = String.format("runStatusId: %s not found", runStatusId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<RunStatus> findByStatus(String status) {
        Result<RunStatus> result = new Result<>();

        if (Validations.isNullOrBlank(status)) {
            result.addMessage("status is required", ResultType.INVALID);
            return result;
        }

        result.setPayload(repository.findByStatus(status));

        if (result.getPayload() == null) {
            String msg = String.format("status: %s not found", status);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public List<RunStatus> findAll() {
        return repository.findAll();
    }

    public Result<RunStatus> add(RunStatus runStatus) {
        Result<RunStatus> result = validate(runStatus);

        if (!result.isSuccess()) {
            return result;
        }

        if (runStatus.getRunStatusId() != 0) {
            result.addMessage("runStatusId cannot be set for `add` operation", ResultType.INVALID);
        } else if (repository.findByStatus(runStatus.getStatus()) != null) {
            result.addMessage("duplicate status are not permitted", ResultType.INVALID);
        } else {
            result.setPayload(repository.add(runStatus));
        }

        return result;
    }

    public Result<RunStatus> update(RunStatus runStatus) {
        Result<RunStatus> result = validate(runStatus);

        if (!result.isSuccess()) {
            return result;
        }

        if (runStatus.getRunStatusId() <= 0) {
            result.addMessage("runStatusId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        RunStatus runStatusFindByStatus = repository.findByStatus(runStatus.getStatus());

        if (runStatusFindByStatus != null && runStatusFindByStatus.getRunStatusId() != runStatus.getRunStatusId()) {
            result.addMessage("duplicate status are not permitted", ResultType.INVALID);
            return result;
        }

        if (!repository.update(runStatus)) {
            String msg = String.format("runStatusId: %s not found", runStatus.getRunStatusId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<RunStatus> validate(RunStatus runStatus) {
        Result<RunStatus> result = new Result<>();

        if (runStatus == null) {
            result.addMessage("runStatus cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(runStatus.getStatus())) {
            result.addMessage("status is required", ResultType.INVALID);
        }

        return result;
    }

}
