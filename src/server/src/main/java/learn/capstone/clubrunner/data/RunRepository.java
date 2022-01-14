package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Run;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RunRepository {
    List<Run> findAll();

    Run findById(int runId);

    List<Run> findByUserId(int userId);

    List<Run> findByClubId(int clubId);

    Run add(Run run);

    boolean update(Run run);

    @Transactional
    boolean deleteById(int runId);


}
