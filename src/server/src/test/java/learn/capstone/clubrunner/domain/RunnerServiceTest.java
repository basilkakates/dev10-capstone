package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunnerRepository;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.Runner;
import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RunnerServiceTest {

    @Autowired
    RunnerService service;

    @MockBean
    RunnerRepository repository;

    @Test
    void shouldFindAll() {
        List<Runner> expected = service.findAll();
        List<Runner> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        Runner runner = makeRunner();
        runner.setRunnerId(1);
        when(repository.findById(runner.getRunnerId())).thenReturn(runner);

        Result<Runner> actual = service.findById(runner.getRunnerId());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(runner, actual.getPayload());
    }

    @Test
    void shouldAdd() {
        Runner runner = makeRunner();
        Result<Runner> result = service.add(runner);
        assertEquals(ResultType.SUCCESS, result.getType());

        result = service.add(runner);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotAddNullRunner() {
        Result<Runner> actual = service.add(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithUserId() {
        Runner runner = makeRunner();
        runner.setRunnerId(1);

        Result<Runner> actual = service.add(runner);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldDelete() {

        service.deleteById(0);
        List<Runner> runners = service.findAll();
        assertTrue(runners.size() == 0);
    }

    Runner makeRunner() {
        Runner runner = new Runner();

        Run run = new Run();
        run.setRunId(1);

        User user = new User();
        user.setUserId(1);

        runner.setRun(run);
        runner.setUser(user);

        return runner;
    }
}
