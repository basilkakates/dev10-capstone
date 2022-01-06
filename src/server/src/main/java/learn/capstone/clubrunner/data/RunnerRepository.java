package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Runner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RunnerRepository {

    List<Runner> findAll();

    @Transactional
    Runner findById(int runner_id);

    Runner add(Runner runner);

    @Transactional
    boolean deleteById(int runner_id);
}
