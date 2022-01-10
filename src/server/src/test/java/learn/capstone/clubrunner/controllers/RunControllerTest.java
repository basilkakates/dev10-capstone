package learn.capstone.clubrunner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.clubrunner.data.RunRepository;
import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.RunStatus;
import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RunControllerTest {

    @MockBean
    RunRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void findAllShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAll()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/run"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByIdShouldReturn404WhenMissing() throws Exception {
        when(repository.findById(anyInt())).thenReturn(null);
        mvc.perform(get("/api/run/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAll()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/run"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn201() throws Exception {
        Run expected = makeRun();
        expected.setRunId(1);

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String jsonIn = jsonMapper.writeValueAsString(makeRun());
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/run")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {

        var request = post("/api/run")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        String agencyJson = jsonMapper.writeValueAsString(new Run());

        var request = post("/api/run")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agencyJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    void addShouldReturn415WhenMultipart() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();

        Run run = new Run();
        String agencyJson = jsonMapper.writeValueAsString(run);

        var request = post("/api/run")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(agencyJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    private Run makeRun() {
        Run run = new Run();

        User user = new User();
        user.setUserId(1);
        user.setFirstName("Testy");
        user.setLastName("McTest");
        user.setEmail("tmctest@test.com");

        Club club = new Club();
        club.setClubId(1);
        club.setName("Test Club");

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus("Test");

        run.setDate(LocalDate.now());
        run.setAddress("000 Test");
        run.setMaxCapacity(25);
        run.setUser(user);
        run.setClub(club);
        run.setRunStatus(runStatus);
        run.setStartTime(LocalTime.now().plusHours(1));
        run.setLatitude(BigDecimal.valueOf(41.902324));
        run.setLongitude(BigDecimal.valueOf(-88.00001));
        run.setDescription("A test run");

        return run;
    }
}
