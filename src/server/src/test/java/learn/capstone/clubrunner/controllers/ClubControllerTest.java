package learn.capstone.clubrunner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.clubrunner.data.ClubRepository;
import learn.capstone.clubrunner.models.Club;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerTest {

    @MockBean
    ClubRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void findAllShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAll()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/club"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByIdShouldReturn404WhenMissing() throws Exception {
        when(repository.findById(anyInt())).thenReturn(null);
        mvc.perform(get("/api/club/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdShouldReturn200() throws Exception {
        Club club = makeClub();
        club.setClubId(1);

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findById(club.getClubId())).thenReturn(club);

        String expectedJson = jsonMapper.writeValueAsString(club);

        String urlTemplate = String.format("/api/club/%s", club.getClubId());
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    private Club makeClub() {
        Club club = new Club();

        club.setClubId(0);
        club.setName("Test Club");
        club.setDescription("Club for test");

        return club;
    }
}
