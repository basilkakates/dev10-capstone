package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Runner;
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
}
