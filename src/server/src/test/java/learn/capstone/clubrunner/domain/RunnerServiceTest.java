package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunnerRepository;
import learn.capstone.clubrunner.models.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

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
        Runner expected = makeRunner();
        when(repository.findById(1)).thenReturn(expected);
        Runner actual = service.findById(1);
        assertEquals(expected, actual);
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
