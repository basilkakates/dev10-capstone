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
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    void shouldNotFindMissingId() {
        assertNull(repository.findById(100000));
    }

    @Test
    void shouldFindRunByUserId() {
        User user = new User();
        user.setUserId(1);
        List<Run> run = repository.findByUserId(1);
        assertNotNull(run);
        assertEquals(1, user.getUserId());
    }

    @Test
    void shouldFindRunByClubId() {
        Club club = new Club();
        club.setClubId(1);
        List<Run> run = repository.findByClubId(1);
        assertNotNull(run);
        assertEquals(1, club.getClubId());
    }

    @Test
    void shouldAddRun() {
        Run run = new Run();

        User user = new User();
        user.setUserId(1);

        Club club = new Club();
        club.setClubId(1);

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(2);

        run.setTimestamp(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
        run.setAddress("000 Test");
        run.setMaxCapacity(25);
        run.setUser(user);
        run.setClub(club);
        run.setRunStatus(runStatus);
        System.out.println(LocalTime.now());
        run.setLatitude(BigDecimal.valueOf(41.902324));
        run.setLongitude(BigDecimal.valueOf(-88.00001));

        Run actual = repository.add(run);
        assertNotNull(actual);
        assertEquals(4, actual.getRunId());
    }

    @Test
    void shouldUpdateRun() {

        Run run = new Run();

        User user = new User();
        user.setUserId(1);

        Club club = new Club();
        club.setClubId(1);

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(2);

        run.setRunId(3);
        run.setTimestamp(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
        run.setAddress("111 One");
        run.setMaxCapacity(26);
        run.setUser(user);
        run.setClub(club);
        run.setRunStatus(runStatus);
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