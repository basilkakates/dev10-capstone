package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Run;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClubJdbcTemplateRepositoryTest {

    @Autowired
    ClubJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindClubs() {
        List<Club> clubs = repository.findAll();
        assertNotNull(clubs);
        assertTrue(clubs.size() > 0);
    }

    @Test
    void shouldFindClubById() {
        Club club = repository.findById(1);
        assertNotNull(club);
        assertEquals(1, club.getClub_id());
    }
}
