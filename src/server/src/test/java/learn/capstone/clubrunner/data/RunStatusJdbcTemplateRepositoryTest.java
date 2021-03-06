package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.RunStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RunStatusJdbcTemplateRepositoryTest {

    @Autowired
    RunStatusJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        RunStatus runStatuses = repository.findById(1);
        assertNotNull(runStatuses);
        assertEquals("Pending Approval", runStatuses.getStatus());
    }

    @Test
    void shouldNotFindMissingId() {
        assertNull(repository.findById(100000));
    }

    @Test
    void shouldFindByStatus() {
        RunStatus testRunStatus = repository.findById(1);
        assertEquals("Pending Approval", testRunStatus.getStatus());
    }

    @Test
    void shouldNotFindMissingStatus() {
        assertNull(repository.findByStatus("MissingStatus"));
    }

    @Test
    void shouldFindAll() {
        assertTrue(repository.findAll().size() >= 3);
    }

    @Test
    void shouldAddRunStatus() {
        RunStatus testRunStatus = new RunStatus();
        testRunStatus.setStatus("Testing");
        RunStatus actual = repository.add(testRunStatus);
        assertNotNull(actual);
        assertEquals("Testing", actual.getStatus());
    }


    @Test
    void shouldUpdateRunStatus() {
        RunStatus testRunStatus = new RunStatus();
        testRunStatus.setRunStatusId(2);
        testRunStatus.setStatus("Pending");
        assertTrue(repository.update(testRunStatus));
        assertEquals(repository.findById(2).getStatus(), "Pending");
    }
}