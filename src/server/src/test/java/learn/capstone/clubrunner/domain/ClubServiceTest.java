package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.ClubRepository;
import learn.capstone.clubrunner.models.Club;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClubServiceTest {

    @Autowired
    ClubService service;

    @MockBean
    ClubRepository repository;

    @Test
    void shouldFindAll() {
        List<Club> expected = service.findAll();
        List<Club> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindClub() {
        Club club = makeClub();
        club.setClubId(1);
        when(repository.findById(club.getClubId())).thenReturn(club);

        Result<Club> actual = service.findById(club.getClubId());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(club, actual.getPayload());
    }

    Club makeClub() {
        Club club = new Club();
        club.setDescription("Test place");
        club.setName("Test Club");

        return club;
    }
}
