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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void findAllShouldReturnList() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAll()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/runStatus"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByIdShouldReturn404WhenMissing() throws Exception {
        when(repository.findById(anyInt())).thenReturn(null);
        mvc.perform(get("/api/runStatus/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdShouldReturn200() throws Exception {
        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus("Test");

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findById(runStatus.getRunStatusId())).thenReturn(runStatus);

        String expectedJson = jsonMapper.writeValueAsString(runStatus);

        mvc.perform(get("/api/runStatus/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByStatusShouldReturn404WhenMissing() throws Exception {
        when(repository.findByStatus(anyString())).thenReturn(null);
        mvc.perform(get("/api/runStatus/status/Test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByStatusShouldReturn200() throws Exception {
        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus("Test");

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findByStatus(anyString())).thenReturn(runStatus);

        String expectedJson = jsonMapper.writeValueAsString(runStatus);

        mvc.perform(get("/api/runStatus/status/Test"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/api/runStatus")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {
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
        when(repository.findByStatus(anyString())).thenReturn(null);

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

    @Test
    void updateShouldReturn400WhenEmpty() throws Exception {
        var request = put("/api/runStatus/1")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(1);
        String runStatusJson = jsonMapper.writeValueAsString(runStatus);

        var request = put("/api/runStatus/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn404WhenMissing() throws Exception {
        when(repository.findByStatus(anyString())).thenReturn(null);
        when(repository.update(any())).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus("Test");

        String runStatusJson = jsonMapper.writeValueAsString(runStatus);

        var request = put("/api/runStatus/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void updateShouldReturn409WhenConflict() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus("Test");

        when(repository.findByStatus(anyString())).thenReturn(null);
        when(repository.update(runStatus)).thenReturn(true);

        String runStatusJson = jsonMapper.writeValueAsString(runStatus);

        var request = put("/api/runStatus/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void updateShouldReturn204() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        RunStatus runStatus = new RunStatus();
        runStatus.setRunStatusId(1);
        runStatus.setStatus("Test");

        when(repository.findByStatus(anyString())).thenReturn(null);
        when(repository.update(runStatus)).thenReturn(true);

        String runStatusJson = jsonMapper.writeValueAsString(runStatus);

        var request = put("/api/runStatus/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }
}
