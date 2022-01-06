package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {
    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldNotFindMissingId() {
        assertNull(repository.findById(100000));
    }

    @Test
    void shouldFindById() {
        User user = repository.findById(1);
        assertNotNull(user);
        assertEquals("jshmoe@test.com", user.getEmail());
    }

    @Test
    void shouldNotFindMissingUserByName() {
        List<User> users = repository.findByName("Test", "McTest");
        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    void shouldFindByName() {
        List<User> users =  repository.findByName("Joe", "Shmoe");
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void shouldNotFindMissingEmail() {
        User user = repository.findByEmail("test@test.com");
        assertNull(user);
    }

    @Test
    void shouldFindByEmail() {
        String email = "jshmoe@test.com";
        User user = repository.findByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    void shouldFindAll() {
        List<User> users = repository.findAll();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void shouldAddUser() {
        User user = buildUser();

        assertEquals(7, repository.add(user).getUser_id());
    }

    @Test
    void shouldUpdateUser() {
        User user = buildUser();
        user.setUser_id(6);
        user.setEmail("Test");

        assertTrue(repository.update(user));

        User actual = repository.findById(6);
        assertEquals(user, actual);
    }

    private User buildUser() {
        User user = new User();
        user.setFirst_name("Testy");
        user.setLast_name("McTester");
        user.setEmail("tmctester@test.com");
        user.setPassword("testerpass");
        return user;
    }
}