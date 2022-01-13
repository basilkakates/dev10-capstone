package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.UserRepository;
import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {
    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void shouldNotFindMissingId() {
        when(repository.findById(100000)).thenReturn(null);

        Result<User> actual = service.findById(100000);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldFindById() {
        User user = makeUser();
        user.setUserId(1);
        when(repository.findById(user.getUserId())).thenReturn(user);

        Result<User> actual = service.findById(user.getUserId());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(user, actual.getPayload());
    }

    @Test
    void shouldNotFindByNullName() {
        Result<List<User>> actualFirstName = service.findByName(null, "McTesty");
        Result<List<User>> actualLastName = service.findByName("Testy", null);

        assertNotNull(actualFirstName);
        assertEquals(ResultType.INVALID, actualFirstName.getType());
        assertNull(actualFirstName.getPayload());

        assertNotNull(actualLastName);
        assertEquals(ResultType.INVALID, actualLastName.getType());
        assertNull(actualLastName.getPayload());
    }

    @Test
    void shouldNotFindByMissingName() {
        List<User> expected = new ArrayList<>();
        User user = makeUser();

        when(repository.findByName(user.getFirstName(), user.getLastName())).thenReturn(expected);

        Result<List<User>> actual = service.findByName(user.getFirstName(), user.getLastName());
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNotNull(actual);
        assertEquals(0, actual.getPayload().size());
    }

    @Test
    void shouldFindByName() {
        User user = makeUser();
        user.setUserId(1);
        when(repository.findByName(user.getFirstName(), user.getLastName())).thenReturn(List.of(user));

        Result<List<User>> actual = service.findByName(user.getFirstName(), user.getLastName());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(user, actual.getPayload().get(0));
    }

    @Test
    void shouldNotFindByNullEmail() {
        Result<User> actual = service.findByEmail(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotFindByMissingEmail() {
        User user = makeUser();
        user.setUserId(1);
        when(repository.findByEmail(user.getEmail())).thenReturn(null);

        Result<User> actual = service.findByEmail(user.getEmail());
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldFindByEmail() {
        User user = makeUser();
        user.setUserId(1);
        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        Result<User> actual = service.findByEmail(user.getEmail());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(user, actual.getPayload());
    }

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(makeUser()));

        List<User> actual = service.findAll();
        assertNotNull(actual);
    }

    @Test
    void shouldNotAddNullUser() {
        Result<User> actual = service.add(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullFirstName() {
        User user = makeUser();
        user.setUserId(1);
        user.setFirstName(null);

        Result<User> actual = service.add(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullLastName() {
        User user = makeUser();
        user.setUserId(1);
        user.setLastName(null);

        Result<User> actual = service.add(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullEmail() {
        User user = makeUser();
        user.setUserId(1);
        user.setEmail(null);

        Result<User> actual = service.add(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullPassword() {
        User user = makeUser();
        user.setUserId(1);
        user.setPasswordHash(null);

        Result<User> actual = service.add(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithUserId() {
        User user = makeUser();
        user.setUserId(1);

        Result<User> actual = service.add(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddDuplicateEmail() {
        User user = makeUser();
        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        Result<User> actual = service.add(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldAddUser() {
        User expected = makeUser();
        expected.setUserId(1);

        when(repository.findByEmail(expected.getEmail())).thenReturn(null);
        when(repository.add(makeUser())).thenReturn(expected);

        Result<User> actual = service.add(makeUser());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotUpdateNullUser() {
        Result<User> actual = service.update(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateNullFirstName() {
        User user = makeUser();
        user.setUserId(1);
        user.setFirstName(null);

        Result<User> actual = service.update(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateNullLastName() {
        User user = makeUser();
        user.setUserId(1);
        user.setLastName(null);

        Result<User> actual = service.update(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateNullEmail() {
        User user = makeUser();
        user.setUserId(1);
        user.setEmail(null);

        Result<User> actual = service.update(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateNullPassword() {
        User user = makeUser();
        user.setUserId(1);
        user.setPasswordHash(null);

        Result<User> actual = service.update(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateMissingId() {
        Result<User> actual = service.update(makeUser());
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWithDuplicateEmail() {
        User user = makeUser();
        user.setUserId(1);

        when(repository.findByEmail(user.getEmail())).thenReturn(makeUser());
        Result<User> actual = service.update(user);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateMissingUser() {
        User user = makeUser();
        user.setUserId(100000);

        when(repository.findByEmail(user.getEmail())).thenReturn(null);
        when(repository.update(user)).thenReturn(false);

        Result<User> actual = service.update(user);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldUpdateUser() {
        User user = makeUser();
        user.setUserId(1);

        when(repository.findByEmail(user.getEmail())).thenReturn(user);
        when(repository.update(user)).thenReturn(true);

        Result<User> actual = service.update(user);
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNull(actual.getPayload());
    }

    private User makeUser() {
        User user = new User();
        user.setUserId(0);
        user.setFirstName("Testy");
        user.setLastName("McTest");
        user.setEmail("tmctesty@test.com");
        user.setPasswordHash("supersecure");
        return user;
    }

}