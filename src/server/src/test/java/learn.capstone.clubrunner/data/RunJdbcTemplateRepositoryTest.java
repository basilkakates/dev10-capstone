package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Run;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RunJdbcTemplateRepositoryTest {

    @Autowired
    RunJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindRuns() {
        List<Run> runs = repository.findAll();
        assertNotNull(runs);
        assertTrue(runs.size() > 0);
    }

    @Test
    void shouldAddRun() {
        Run run = new Run();
        run.setDate(LocalDate.parse("2025-11-01"));
        run.setAddress("000 Test");
        run.setMax_capacity(25);
        run.setStart_time(LocalTime.parse("13:30"));
        run.setLatitude(BigDecimal.valueOf(41.902324));
        run.setLongitude(BigDecimal.valueOf(-88.00001));
        run.setUser_id(1);
        run.setClub_id(1);
        run.setRun_status_id(2);
        Run actual = repository.add(run);
        assertNotNull(actual);
        assertEquals(4, actual.getRun_id());
    }

    @Test
    void shouldUpdateRun() {

        Run run = new Run();
        run.setRun_id(3);
        run.setDate(LocalDate.parse("2025-11-11"));
        run.setAddress("111 One");
        run.setMax_capacity(26);
        run.setStart_time(LocalTime.parse("13:30"));
        run.setLatitude(BigDecimal.valueOf(41.902324));
        run.setLongitude(BigDecimal.valueOf(-88.00001));
        run.setUser_id(1);
        run.setClub_id(1);
        run.setRun_status_id(2);

        assertTrue(repository.update(run));
    }

    @Test
    void shouldDeleteRun() {
        repository.deleteById(4);
        List<Run> runs = repository.findAll();
        assertNotNull(runs);
        assertTrue(runs.size() == 3);
    }
}