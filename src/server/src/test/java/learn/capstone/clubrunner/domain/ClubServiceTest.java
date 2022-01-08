package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.ClubRepository;
import learn.capstone.clubrunner.models.Club;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClubServiceTest {

    @Autowired
    ClubService service;

    @MockBean
    ClubRepository repository;

    @Test
    void shouldFindAll() {
        List<Club> expected = service.findAll();
        List<Club> actual =  service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindClub() {
        Club expected = makeClub();
        when(repository.findById(1)).thenReturn(expected);
        Club actual = service.findById(1);
        assertEquals(expected, actual);
    }

    Club makeClub() {
        Club club = new Club();
        club.setDescription("Test place");
        club.setName("Test Club");

        return club;
    }
}
