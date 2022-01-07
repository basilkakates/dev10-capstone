package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.RunStatusRepository;
import learn.capstone.clubrunner.models.RunStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RunStatusServiceTest {
    @Autowired
    RunStatusService service;

    @MockBean
    RunStatusRepository repository;

    @Test
    void shouldNotFindMissingId() {
        when(repository.findById(100000)).thenReturn(null);

        Result<RunStatus> actual = service.findById(100000);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldFindById() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(1);
        when(repository.findById(runStatus.getRunStatusId())).thenReturn(runStatus);

        Result<RunStatus> actual = service.findById(runStatus.getRunStatusId());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(runStatus, actual.getPayload());
    }

    @Test
    void shouldNotFindByNullStatus() {
        Result<RunStatus> actual = service.findByStatus(null);

        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotFindByMissingStatus() {
        String missingStatus = "Missing";
        when(repository.findByStatus(missingStatus)).thenReturn(null);

        Result<RunStatus> actual = service.findByStatus(missingStatus);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldFindByStatus() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(1);
        when(repository.findByStatus(runStatus.getStatus())).thenReturn(runStatus);

        Result<RunStatus> actual = service.findByStatus(runStatus.getStatus());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(runStatus, actual.getPayload());
    }

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(makeRunStatus()));

        List<RunStatus> actual = service.findAll();
        assertNotNull(actual);
    }

    @Test
    void shouldNotAddNullRunStatus() {
        Result<RunStatus> actual = service.add(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullStatus() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setStatus(null);

        Result<RunStatus> actual = service.add(runStatus);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithId() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(1);

        Result<RunStatus> actual = service.add(runStatus);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithDuplicateStatus() {
        RunStatus runStatus = makeRunStatus();
        when(repository.findByStatus(runStatus.getStatus())).thenReturn(runStatus);

        Result<RunStatus> actual = service.add(runStatus);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldAddRunStatus() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(1);

        when(repository.findByStatus(runStatus.getStatus())).thenReturn(null);
        when(repository.add(makeRunStatus())).thenReturn(runStatus);

        Result<RunStatus> actual = service.add(makeRunStatus());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(runStatus, actual.getPayload());
    }

    @Test
    void shouldNotUpdateNullRunStatus() {
        Result<RunStatus> actual = service.update(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateNullStatus() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus(null);

        Result<RunStatus> actual = service.update(runStatus);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWithoutRunStatusId() {
        Result<RunStatus> actual = service.update(makeRunStatus());
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateDuplicateStatus() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(1);
        when(repository.findByStatus(runStatus.getStatus())).thenReturn(makeRunStatus());

        Result<RunStatus> actual = service.update(runStatus);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateMissingId() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(100000);

        when(repository.findByStatus(runStatus.getStatus())).thenReturn(null);
        when(repository.update(runStatus)).thenReturn(false);

        Result<RunStatus> actual = service.update(runStatus);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldUpdateRunStatus() {
        RunStatus runStatus = makeRunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus("Updated");

        when(repository.findByStatus(runStatus.getStatus())).thenReturn(null);
        when(repository.update(runStatus)).thenReturn(true);

        Result<RunStatus> actual = service.update(runStatus);
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    private RunStatus makeRunStatus() {
        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(0);
        runStatus.setStatus("Test");
        return runStatus;
    }

}