package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Run;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RunRepository {
    List<Run> findAll();

    @Transactional
    Run findById(int run_id);

    Run add(Run run);

    boolean update(Run run);

    @Transactional
    boolean deleteById(int run_id);
}
