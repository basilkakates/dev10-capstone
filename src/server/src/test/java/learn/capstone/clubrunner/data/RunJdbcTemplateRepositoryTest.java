package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.RunStatus;
import learn.capstone.clubrunner.models.User;
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
    void shouldFindRunById() {
        Run run = repository.findById(1);
        assertNotNull(run);
        assertEquals(1, run.getRun_id());
    }

    @Test
    void shouldAddRun() {
        Run run = new Run();

        User user = new User();
        user.setUser_id(1);

        Club club = new Club();
        club.setClub_id(1);

        RunStatus runStatus = new RunStatus();
        runStatus.setRun_status_id(2);

        run.setDate(LocalDate.parse("2025-11-01"));
        run.setAddress("000 Test");
        run.setMax_capacity(25);
        run.setUser(user);
        run.setClub(club);
        run.setRunStatus(runStatus);
        run.setStart_time(LocalTime.parse("13:30"));
        run.setLatitude(BigDecimal.valueOf(41.902324));
        run.setLongitude(BigDecimal.valueOf(-88.00001));

        Run actual = repository.add(run);
        assertNotNull(actual);
        assertEquals(4, actual.getRun_id());
    }

    @Test
    void shouldUpdateRun() {

        Run run = new Run();

        User user = new User();
        user.setUser_id(1);

        Club club = new Club();
        club.setClub_id(1);

        RunStatus runStatus = new RunStatus();
        runStatus.setRun_status_id(2);

        run.setRun_id(3);
        run.setDate(LocalDate.parse("2025-11-11"));
        run.setAddress("111 One");
        run.setMax_capacity(26);
        run.setUser(user);
        run.setClub(club);
        run.setRunStatus(runStatus);
        run.setStart_time(LocalTime.parse("13:30"));
        run.setLatitude(BigDecimal.valueOf(41.902324));
        run.setLongitude(BigDecimal.valueOf(-88.00001));

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