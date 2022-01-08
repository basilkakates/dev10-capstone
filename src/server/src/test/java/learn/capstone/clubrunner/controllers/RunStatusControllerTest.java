package learn.capstone.clubrunner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.clubrunner.data.RunStatusRepository;
import learn.capstone.clubrunner.models.RunStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RunStatusControllerTest {
    @MockBean
    RunStatusRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturn400WhenEmpty() throws Exception {
        var request = post("/api/runStatus")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        RunStatus runStatus = new RunStatus();
        String runStatusJson = jsonMapper.writeValueAsString(runStatus);

        var request = post("/api/runStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn415WhenMultipart() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(0);
        runStatus.setStatus("Test");

        String runStatusJson = jsonMapper.writeValueAsString(runStatus);

        var request = post("/api/runStatus")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentType(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void addShouldReturn201() throws Exception {
        RunStatus runStatus = new RunStatus();
        runStatus.setStatus("Test");

        RunStatus expected = new RunStatus();
        expected.setStatus("Test");
        expected.setRunStatusId(1);

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String runStatusJson = jsonMapper.writeValueAsString(runStatus);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/runStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }
}
