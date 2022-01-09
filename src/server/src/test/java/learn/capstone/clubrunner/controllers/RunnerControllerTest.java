package learn.capstone.clubrunner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.clubrunner.data.RunnerRepository;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.Runner;
import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
class RunnerControllerTest {

    @MockBean
    RunnerRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void findAllShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAll()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/runner"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByIdShouldReturn404WhenMissing() throws Exception {
        when(repository.findById(anyInt())).thenReturn(null);
        mvc.perform(get("/api/runner/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdShouldReturn200() throws Exception {
        Runner runner = makeRunner();
        runner.setRunnerId(1);

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findById(runner.getRunnerId())).thenReturn(runner);

        String expectedJson = jsonMapper.writeValueAsString(runner);

        String urlTemplate = String.format("/api/runner/%s", runner.getRunnerId());
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn400WhenEmpty() throws Exception {
        var request = post("/api/runner")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Runner runner = new Runner();
        runner.setRunnerId(0);

        Run run = new Run();
        run.setRunId(0);

        User user = new User();
        user.setUserId(0);

        runner.setUser(user);
        runner.setRun(run);

        String runnerJason = jsonMapper.writeValueAsString(runner);

        var request = post("/api/runner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runnerJason);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn415WhenMultipart() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Runner runner = new Runner();
        runner.setRunnerId(0);

        String runnerJson = jsonMapper.writeValueAsString(runner);

        var request = post("/api/runner")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentType(runnerJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void addShouldReturn201() throws Exception {
        Runner runner = new Runner();

        Runner expected = new Runner();
        expected.setRunnerId(1);

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String runnerJson = jsonMapper.writeValueAsString(runner);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/runner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runnerJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    private Runner makeRunner() {
        Runner runner = new Runner();
        runner.setRunnerId(0);

        Run run = new Run();
        run.setRunId(1);

        User user = new User();
        user.setUserId(1);

        runner.setUser(user);
        runner.setRun(run);

        return runner;
    }
}