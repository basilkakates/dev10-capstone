package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Member;
import learn.capstone.clubrunner.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberJdbcTemplateRepositoryTest {
    @Autowired
    MemberJdbcTemplateRepository repository;

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
        Member member = repository.findById(1);
        assertNotNull(member);
        assertNotNull(member.getClub());
        assertNotNull(member.getUser());
    }

    @Test
    void shouldNotFindByMissingUserId() {
        List<Member> memberships = repository.findByUserId(100000);
        assertNotNull(memberships);
        assertEquals(0, memberships.size());
    }

    @Test
    void shouldFindByUserId() {
        int userId = 3;

        List<Member> memberships = repository.findByUserId(userId);
        assertNotNull(memberships);
        assertTrue(memberships.size() > 0);

        assertNotNull(memberships.get(0).getUser());
        assertNotNull(memberships.get(0).getClub());

        assertEquals(userId, memberships.get(0).getUser().getUser_id());
    }

    @Test
    void shouldNotFindByMissingClubId() {
        List<Member> memberships = repository.findByClubId(100000);
        assertNotNull(memberships);
        assertEquals(0, memberships.size());
    }

    @Test
    void shouldFindByClubId() {
        int clubId = 1;

        List<Member> memberships = repository.findByClubId(clubId);
        assertNotNull(memberships);
        assertTrue(memberships.size() > 0);

        assertNotNull(memberships.get(0).getUser());
        assertNotNull(memberships.get(0).getClub());

        assertEquals(clubId, memberships.get(0).getClub().getClub_id());
    }

    @Test
    void shouldFindAdmins() {
        List<Member> memberships = repository.findAdmins();
        assertNotNull(memberships);
        assertTrue(memberships.size() > 0);

        assertNotNull(memberships.get(0).getUser());
        assertNotNull(memberships.get(0).getClub());

        assertEquals(1, memberships.get(0).getIsAdmin());
    }

    @Test
    void shouldFindAll() {
        List<Member> memberships = repository.findAll();
        assertNotNull(memberships);
        assertTrue(memberships.size() > 0);

        assertNotNull(memberships.get(0).getUser());
        assertNotNull(memberships.get(0).getClub());
    }

    @Test
    void shouldAddMember() {
        Member member = buildMember();

        Member actual = repository.add(member);

        assertNotNull(actual);
        assertNotNull(actual.getClub());
        assertNotNull(actual.getUser());

        assertEquals(7, actual.getMember_id());
    }

    @Test
    void shouldUpdateMember() {
        int isAdmin = 1;

        Member member = buildMember();
        member.setMember_id(1);
        member.getClub().setClub_id(1);
        member.setIsAdmin(isAdmin);

        assertTrue(repository.update(member));

        Member actual = repository.findById(1);
        assertEquals(isAdmin, actual.getIsAdmin());
    }

    @Test
    void shouldDeleteMember() {
        int memberId = 6;
        assertNotNull(repository.findById(memberId));
        assertTrue(repository.deleteById(memberId));
    }

    private Member buildMember() {
        Club club = new Club();
        club.setClub_id(2);

        User user = new User();
        user.setUser_id(1);

        Member member = new Member();
        member.setClub(club);
        member.setUser(user);
        member.setIsAdmin(0);

        return member;
    }
}