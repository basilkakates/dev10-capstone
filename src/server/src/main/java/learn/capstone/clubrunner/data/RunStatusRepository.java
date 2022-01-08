package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.RunStatus;

import java.util.List;

public interface RunStatusRepository {
    RunStatus findById(int runStatusId);

    RunStatus findByStatus(String status);

    List<RunStatus> findAll();

    RunStatus add(RunStatus runStatus);

    boolean update(RunStatus runStatus);
}
