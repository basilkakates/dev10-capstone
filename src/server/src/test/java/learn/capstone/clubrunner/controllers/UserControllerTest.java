package learn.capstone.clubrunner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.clubrunner.data.UserRepository;
import learn.capstone.clubrunner.models.Run;
import learn.capstone.clubrunner.models.User;
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
class UserControllerTest {
    @MockBean
    UserRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void findAllShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAll()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByIdShouldReturn404WhenMissing() throws Exception {
        when(repository.findById(anyInt())).thenReturn(null);
        mvc.perform(get("/api/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdShouldReturn200() throws Exception {
        User user = makeUser();
        user.setUserId(1);

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findById(user.getUserId())).thenReturn(user);

        String expectedJson = jsonMapper.writeValueAsString(user);

        String urlTemplate = String.format("/api/user/%s", user.getUserId());
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByNameShouldReturn400WhenInvalid() throws Exception {
        mvc.perform(get("/api/user/name"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByNameShouldReturn404WhenMissing() throws Exception {
        when(repository.findByName(anyString(), anyString())).thenReturn(new ArrayList<>());

        ObjectMapper jsonMapper = new ObjectMapper();
        String[] name = {"Testy", "McTest"};
        String nameJson = jsonMapper.writeValueAsString(name);

        mvc.perform(get("/api/user/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nameJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByNameShouldReturn200() throws Exception {
        User user = makeUser();
        user.setUserId(1);

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findByName(user.getFirstName(), user.getLastName())).thenReturn(List.of(user));

        String[] name = {user.getFirstName(), user.getLastName()};
        String nameJson = jsonMapper.writeValueAsString(name);
        String expectedJson = jsonMapper.writeValueAsString(List.of(user));

        mvc.perform(get("/api/user/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nameJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByEmailShouldReturn400WhenInvalid() throws Exception {
        mvc.perform(get("/api/user/email"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByEmailShouldReturn404WhenMissing() throws Exception {
        when(repository.findByEmail(anyString())).thenReturn(null);

        ObjectMapper jsonMapper = new ObjectMapper();
        String emailJson = jsonMapper.writeValueAsString("tmctest@test.com");

        mvc.perform(get("/api/user/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByEmailShouldReturn200() throws Exception {
        User user = makeUser();
        user.setUserId(1);

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        String expectedJson = jsonMapper.writeValueAsString(user);

        mvc.perform(get("/api/user/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }


    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/api/user")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        String userJson = jsonMapper.writeValueAsString(new User());

        var request = post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn415WhenMultipart() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        String runStatusJson = jsonMapper.writeValueAsString(makeUser());

        var request = post("/api/user")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentType(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void addShouldReturn201() throws Exception {
        User expected = makeUser();
        expected.setUserId(1);

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String userJson = jsonMapper.writeValueAsString(makeUser());
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void updateShouldReturn400WhenEmpty() throws Exception {
        var request = put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        User user = new User();
        user.setUserId(1);

        String runStatusJson = jsonMapper.writeValueAsString(user);

        String urlTemplate = String.format("/api/user/%s", user.getUserId());

        var request = put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn404WhenMissing() throws Exception {
        when(repository.findByEmail(anyString())).thenReturn(null);
        when(repository.update(any())).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();

        User user = makeUser();
        user.setUserId(1);

        String runStatusJson = jsonMapper.writeValueAsString(user);

        String urlTemplate = String.format("/api/user/%s", user.getUserId());

        var request = put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void updateShouldReturn415WhenMultipart() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        User user = makeUser();
        user.setUserId(1);

        when(repository.findByEmail(anyString())).thenReturn(null);
        when(repository.update(user)).thenReturn(true);

        String userJson = jsonMapper.writeValueAsString(user);

        String urlTemplate = String.format("/api/user/%s", user.getUserId());

        var request = put(urlTemplate)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void updateShouldReturn409WhenConflict() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        User user = makeUser();
        user.setUserId(1);

        when(repository.findByEmail(anyString())).thenReturn(null);
        when(repository.update(user)).thenReturn(true);

        String userJson = jsonMapper.writeValueAsString(user);

        var request = put("/api/user/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void updateShouldReturn204() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        User user = makeUser();
        user.setUserId(1);

        when(repository.findByEmail(anyString())).thenReturn(null);
        when(repository.update(user)).thenReturn(true);

        String userJson = jsonMapper.writeValueAsString(user);

        String urlTemplate = String.format("/api/user/%s", user.getUserId());

        var request = put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }



    private User makeUser() {
        User user = new User();
        user.setUserId(0);
        user.setFirstName("Testy");
        user.setLastName("McTest");
        user.setEmail("tmctest@test.com");
        user.setPasswordHash("supersecure");

        return user;
    }
}