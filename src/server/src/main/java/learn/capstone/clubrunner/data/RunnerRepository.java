package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Runner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RunnerRepository {

    List<Runner> findAll();

    @Transactional
    Runner findById(int runnerId);

    Runner add(Runner runner);

    @Transactional
    boolean deleteById(int runnerId);
}
