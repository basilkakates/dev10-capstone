package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.Runner;
import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RunnerJdbcTemplateRepositoryTest {

    @Autowired
    RunnerJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindRunners() {
        List<Runner> runners = repository.findAll();
        assertNotNull(runners);
        assertTrue(runners.size() > 0);
    }

    @Test
    void shouldFindRunnerById() {
        Runner runner = repository.findById(1);
        assertNotNull(runner);
        assertEquals(1, runner.getRunner_id());
    }

    @Test
    void shouldAddRunner() {

        Runner runner = new Runner();

        User user = new User();
        user.setUser_id(2);

        Run run = new Run();
        run.setRun_id(1);

        runner.setUser(user);
        runner.setRun(run);

        Runner actual = repository.add(runner);
        assertNotNull(actual);
        assertEquals(6, actual.getRunner_id());
    }

    @Test
    void shouldDeleteRunner() {
        repository.deleteById(5);
        List<Runner> runners = repository.findAll();
        assertNotNull(runners);
        assertTrue(runners.size() == 5);
    }
}
