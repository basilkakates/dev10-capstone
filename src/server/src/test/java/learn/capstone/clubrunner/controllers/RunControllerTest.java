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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
    void addShouldReturn201() throws Exception {
        // 1. Configure per-test mock repository behavior.
        Run run = makeRun();
        Run expected = makeRun();
        expected.setRunId(1);

        when(repository.add(any())).thenReturn(expected);

        // 2. Generate both input and expected JSON.
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(run);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        // 3. Build the request.
        var request = post("/api/run")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        // 4. Send the request and assert.
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindAll() throws Exception{
//        List<Run> runs = List.of(
//                makeRun()
//        );
//        ObjectMapper jsonMapper = new ObjectMapper();
//        String expectedJson = jsonMapper.writeValueAsString(runs);
//
//        when(repository.findAll(true)).thenReturn(runs);
//
//        mvc.perform(get("/api/run"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindById() {

    }

    @Test
    void shouldUpdate() {

    }

    @Test
    void shouldDeleteById() {

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

        Run run = makeRun();
        String agencyJson = jsonMapper.writeValueAsString(run);

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
        run.setLatitude(new BigDecimal("41.902324"));
        run.setLongitude(new BigDecimal("-88.00001"));
        run.setDescription("");

        return run;
    }
}
