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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RunServiceTest {

    @Autowired
    RunService service;

    @MockBean
    RunRepository repository;

    @Test
    void shouldFindAll() {
        List<Run> expected = service.findAll();
        List<Run> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindRun() {
        Run expected = makeRun();
        when(repository.findById(1)).thenReturn(expected);
        Run actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        Run run = makeRun();
        Result<Run> result = service.add(run);
        assertEquals(ResultType.SUCCESS, result.getType());

        run.setRun_id(0);
        result = service.add(run);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    Run makeRun() {
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

        return run;
    }
}
