package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Runner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RunnerRepository {

    List<Runner> findAll();

    Runner findById(int runnerId);

    List<Runner> findByUserId(int userId);

    List<Runner> findByRunId(int clubId);

    Runner add(Runner runner);

    @Transactional
    boolean deleteById(int runnerId);
}
