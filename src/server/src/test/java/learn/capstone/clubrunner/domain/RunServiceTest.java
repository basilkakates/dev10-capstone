package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunRepository;
import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.RunStatus;
import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RunServiceTest {

    @Autowired
    RunService service;

    @MockBean
    RunRepository repository;

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(makeRun()));

        List<Run> actual = service.findAll();
        assertNotNull(actual);
    }

    @Test
    void shouldFindRunById() {
        Run run = makeRun();
        run.setRunId(1);
        when(repository.findById(run.getRunId())).thenReturn(run);

        Result<Run> actual = service.findById(run.getRunId());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(run, actual.getPayload());
    }

    @Test
    void shouldNotFindMissingId() {
        when(repository.findById(100000)).thenReturn(null);

        Result<Run> actual = service.findById(100000);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND ,actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldAdd() {
        Run run = makeRun();
        Result<Run> result = service.add(run);
        assertEquals(ResultType.SUCCESS, result.getType());

        result = service.add(run);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotAddNullRun() {
        Result<Run> actual = service.add(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullAddress() {
        Run run = makeRun();
        run.setAddress(null);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullDate() {
        Run run = makeRun();
        run.setDate(null);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullDateInPast() {
        Run run = makeRun();
        run.setDate(LocalDate.parse("2022-01-01"));

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddZeroMaxCapacity() {
        Run run = makeRun();
        run.setMaxCapacity(0);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNegativeMaxCapacity() {
        Run run = makeRun();
        run.setMaxCapacity(-1);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullStartTime() {
        Run run = makeRun();
        run.setStartTime(null);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddStartTimeInThePast() {
        Run run = makeRun();
        run.setStartTime(LocalTime.parse("00:00"));

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullLatitude() {
        Run run = makeRun();
        run.setLatitude(null);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullLongitude() {
        Run run = makeRun();
        run.setLongitude(null);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithRunId() {
        Run run = makeRun();
        run.setRunId(1);

        Result<Run> actual = service.add(run);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldUpdate() {
        Run run = makeRun();
        run.setRunId(1);

        when(repository.update(run)).thenReturn(true);

        Result<Run> actual = service.update(run);
        assertEquals(ResultType.SUCCESS, actual.getType());

    }

    @Test
    void shouldNotUpdateNullRun() {
        Result<Run> actual = service.update(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateZeroOrNegativeId() {
        Run run = makeRun();
        run.setRunId(0);

        when(repository.update(run)).thenReturn(true);

        Result<Run> actual = service.update(run);
        assertEquals(ResultType.INVALID, actual.getType());

    }

    @Test
    void shouldNotUpdateMissingId() {
        Result<Run> actual = service.update(makeRun());
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());

    }

    @Test
    void shouldNotUpdateMissingRun() {
        Run run = makeRun();
        run.setRunId(100000);

        when(repository.update(run)).thenReturn(false);

        Result<Run> actual = service.update(run);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldDelete() {

        service.deleteById(0);
        List<Run> runs = service.findAll();
        assertTrue(runs.size() == 0);
    }

    Run makeRun() {
        Run run = new Run();

        User user = new User();
        user.setUserId(1);

        Club club = new Club();
        club.setClubId(1);

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(2);

        run.setDate(LocalDate.now().plusYears(10));
        run.setAddress("000 Test");
        run.setMaxCapacity(25);
        run.setUser(user);
        run.setClub(club);
        run.setRunStatus(runStatus);
        run.setStartTime(LocalTime.now().plusHours(1));
        run.setLatitude(BigDecimal.valueOf(41.902324));
        run.setLongitude(BigDecimal.valueOf(-88.00001));

        return run;
    }
}
