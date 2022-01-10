package learn.capstone.clubrunner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.clubrunner.data.MemberRepository;
import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Member;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @MockBean
    MemberRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void findAllShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAll()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/member"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findAdminsShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findAdmins()).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/member/admins"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByUserIdShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findByUserId(anyInt())).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/member/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByClubIdShouldReturn200() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findByClubId(anyInt())).thenReturn(new ArrayList<>());
        String expectedJson = jsonMapper.writeValueAsString(new ArrayList<>());

        mvc.perform(get("/api/member/club/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findByIdShouldReturn404WhenMissing() throws Exception {
        when(repository.findById(anyInt())).thenReturn(null);
        mvc.perform(get("/api/member/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdShouldReturn200() throws Exception {
        Member member = makeMember();
        member.setMemberId(1);

        ObjectMapper jsonMapper = new ObjectMapper();
        when(repository.findById(member.getMemberId())).thenReturn(member);

        String expectedJson = jsonMapper.writeValueAsString(member);

        String urlTemplate = String.format("/api/member/%s", member.getMemberId());
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/api/member")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        String userJson = jsonMapper.writeValueAsString(new Member());

        var request = post("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn415WhenMultipart() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        String runStatusJson = jsonMapper.writeValueAsString(makeMember());

        var request = post("/api/member")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentType(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void addShouldReturn201() throws Exception {
        Member expected = makeMember();
        expected.setMemberId(1);

        when(repository.add(any())).thenReturn(expected);
        when(repository.findByUserId(anyInt())).thenReturn(new ArrayList<>());

        ObjectMapper jsonMapper = new ObjectMapper();

        String userJson = jsonMapper.writeValueAsString(makeMember());
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void updateShouldReturn400WhenEmpty() throws Exception {
        var request = put("/api/member/1")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Member member = new Member();
        member.setMemberId(1);

        String runStatusJson = jsonMapper.writeValueAsString(member);

        String urlTemplate = String.format("/api/member/%s", member.getMemberId());

        var request = put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn404WhenMissing() throws Exception {
        when(repository.findByUserId(anyInt())).thenReturn(new ArrayList<>());
        when(repository.update(any())).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();

        Member member = makeMember();
        member.setMemberId(1);

        String runStatusJson = jsonMapper.writeValueAsString(member);

        String urlTemplate = String.format("/api/member/%s", member.getMemberId());

        var request = put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(runStatusJson);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void updateShouldReturn415WhenMultipart() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Member member = makeMember();
        member.setMemberId(1);

        when(repository.findByUserId(anyInt())).thenReturn(new ArrayList<>());
        when(repository.update(member)).thenReturn(true);

        String userJson = jsonMapper.writeValueAsString(member);

        String urlTemplate = String.format("/api/member/%s", member.getMemberId());

        var request = put(urlTemplate)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void updateShouldReturn409WhenConflict() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Member member = makeMember();
        member.setMemberId(1);

        when(repository.findByUserId(anyInt())).thenReturn(new ArrayList<>());
        when(repository.update(member)).thenReturn(true);

        String userJson = jsonMapper.writeValueAsString(member);

        var request = put("/api/member/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void updateShouldReturn204() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Member member = makeMember();
        member.setMemberId(1);

        when(repository.findByUserId(anyInt())).thenReturn(new ArrayList<>());
        when(repository.update(member)).thenReturn(true);

        String userJson = jsonMapper.writeValueAsString(member);

        String urlTemplate = String.format("/api/member/%s", member.getMemberId());

        var request = put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldReturn404WhenMissing() throws Exception {
        when(repository.deleteById(anyInt())).thenReturn(false);
        mvc.perform(delete("/api/member/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        when(repository.deleteById(anyInt())).thenReturn(true);
        mvc.perform(delete("/api/member/1"))
                .andExpect(status().isNoContent());
    }

    private Member makeMember() {
        Member member = new Member();
        member.setMemberId(0);

        User user = new User();
        user.setUserId(1);
        user.setFirstName("Testy");
        user.setLastName("McTest");
        user.setEmail("tmctest@test.com");

        Club club = new Club();
        club.setClubId(1);
        club.setName("Test");

        member.setUser(user);
        member.setClub(club);
        member.setIsAdmin(0);

        return member;
    }
}