package learn.capstone.clubrunner.data;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvrionment = SpringBootTest.WebEnvironment.NONE)
class RunStatusJdbcTemplateRepositoryTest {

    @Autowired
    RunStatusJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() { knownGoodState.set(); }

    @Test
    void shouldFindById() {
        List<RunStatus> runStatuses = repository.findAll();
        assertNotNull(runStatuses);
        asserTrue(runStatuses.size() > 0);
    }

    @Test
    void shouldFindByStatus() {
        RunStatus testRunStatus = repository.findById(1);
        assertEquals("", testRunStatus.getStatus());
    }

    @Test
    void shouldAddRunStatus() {
        RunStatus testRunStatus = new RunStatus();
        testRunStatus.setStatus("Approved");
        RunStatus actual = repository.add(testRunStatus);
        assertNotNull(actual);
        assertEquals("Approved", actual.getRunStatus());
    }


    @Test
    void shouldUpdateRunStatus() {
        RunStatus testRunStatus = new RunStatus();
        testRunStatus.setId(1);
        testRunStatus.setStatus("Pending");
        assertTrue(repository.update(testRunStatus));
        assertEquals(repository.findById(1).status, "Pending");
    }

    @Test
    void shouldDeleteRunStatus() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }
}