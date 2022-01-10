package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.MemberRepository;
import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.Member;
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
class MemberServiceTest {
    @Autowired
    MemberService service;

    @MockBean
    MemberRepository repository;

    @Test
    void shouldNotFindMissingId() {
        when(repository.findByUserId(100000)).thenReturn(null);

        Result<Member> actual = service.findById(100000);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldFindById() {
        Member member = makeMember();
        when(repository.findById(member.getMemberId())).thenReturn(member);

        Result<Member> actual = service.findById(member.getMemberId());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(member, actual.getPayload());
    }

    @Test
    void shouldFindByMissingUserId() {
        when(repository.findByUserId(100000)).thenReturn(new ArrayList<>());
        List<Member> actual = service.findByUserId(100000);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldFindByUserId() {
        List<Member> memberList = new ArrayList<>();
        Member member = makeMember();
        memberList.add(makeMember());
        memberList.add(makeMember());

        when(repository.findByUserId(member.getUser().getUserId())).thenReturn(memberList);
        List<Member> actual = service.findByUserId(member.getUser().getUserId());
        assertNotNull(actual);
        assertEquals(memberList, actual);
    }

    @Test
    void shouldNotFindByMissingClubId() {
        when(repository.findByClubId(100000)).thenReturn(new ArrayList<>());
        List<Member> actual = service.findByClubId(100000);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldFindByClubId() {
        List<Member> memberList = new ArrayList<>();
        Member member = makeMember();
        memberList.add(makeMember());
        memberList.add(makeMember());

        when(repository.findByClubId(member.getClub().getClubId())).thenReturn(memberList);
        List<Member> actual = service.findByClubId(member.getClub().getClubId());
        assertNotNull(actual);
        assertEquals(memberList, actual);
    }

    @Test
    void shouldFindAdmins() {
        List<Member> adminList = new ArrayList<>();
        Member member = makeMember();
        member.setIsAdmin(1);
        adminList.add(member);
        adminList.add(member);

        when(repository.findAdmins()).thenReturn(adminList);
        List<Member> actual = service.findAdmins();
        assertNotNull(actual);
        assertEquals(adminList, actual);
    }

    @Test
    void shouldFindAll() {
        List<Member> memberList = new ArrayList<>();
        Member member = makeMember();
        memberList.add(member);
        memberList.add(member);

        when(repository.findAll()).thenReturn(memberList);
        List<Member> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(memberList, actual);
    }

    @Test
    void shouldNotAddNullMember() {
        Result<Member> actual = service.add(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithNullUser() {
        Member member = makeMember();
        member.setUser(null);

        Result<Member> actual = service.add(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithNullClub() {
        Member member = makeMember();
        member.setClub(null);

        Result<Member> actual = service.add(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithMissingUserId() {
        Member member = makeMember();
        member.getUser().setUserId(0);

        Result<Member> actual = service.add(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithMissingClubId() {
        Member member = makeMember();
        member.getClub().setClubId(0);

        Result<Member> actual = service.add(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithSetMemberId() {
        Member member = makeMember();
        member.setMemberId(1);

        Result<Member> actual = service.add(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddDuplicateMemberships() {
        Member member = makeMember();
        when(repository.findByUserId(member.getUser().getUserId())).thenReturn(List.of(member));

        Result<Member> actual = service.add(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWhenAlreadyAnAdmin() {
        Member memberReturn = makeMember();
        memberReturn.setIsAdmin(1);

        Member member = makeMember();
        member.setIsAdmin(1);
        member.getClub().setClubId(2);

        when(repository.findByUserId(member.getUser().getUserId())).thenReturn(List.of(memberReturn));

        Result<Member> actual = service.add(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldAdd() {
        Member member = makeMember();
        member.setMemberId(1);

        when(repository.findByUserId(member.getUser().getUserId())).thenReturn(new ArrayList<>());
        when(repository.add(makeMember())).thenReturn(member);

        Result<Member> actual = service.add(makeMember());
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNotNull(actual.getPayload());
        assertEquals(member, actual.getPayload());
    }

    @Test
    void shouldNotUpdateNullMember() {
        Result<Member> actual = service.update(null);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWithNullUser() {
        Member member = makeMember();
        member.setMemberId(1);
        member.setUser(null);

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWithNullClub() {
        Member member = makeMember();
        member.setMemberId(1);
        member.setClub(null);

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWithMissingUserId() {
        Member member = makeMember();
        member.setMemberId(1);
        member.getUser().setUserId(0);

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWithMissingClubId() {
        Member member = makeMember();
        member.setMemberId(1);
        member.getClub().setClubId(0);

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWithMissingMemberId() {
        Member member = makeMember();

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWithDuplicateMembership() {
        Member member = makeMember();
        member.setMemberId(1);

        Member memberReturn = makeMember();
        member.setMemberId(2);

        when(repository.findByUserId(member.getUser().getUserId())).thenReturn(List.of(memberReturn));

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateWhenAlreadyAnAdmin() {
        Member memberReturn = makeMember();
        memberReturn.setMemberId(1);
        memberReturn.setIsAdmin(1);

        Member member = makeMember();
        member.setMemberId(2);
        member.setIsAdmin(1);
        member.getClub().setClubId(2);

        when(repository.findByUserId(member.getUser().getUserId())).thenReturn(List.of(memberReturn));

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.INVALID, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateMissingMember() {
        Member member = makeMember();
        member.setMemberId(100000);

        when(repository.findByUserId(member.getMemberId())).thenReturn(new ArrayList<>());
        when(repository.update(member)).thenReturn(false);

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldUpdate() {
        Member member = makeMember();
        member.setMemberId(1);

        when(repository.findByUserId(member.getMemberId())).thenReturn(List.of(member));
        when(repository.update(member)).thenReturn(true);

        Result<Member> actual = service.update(member);
        assertNotNull(actual);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotDeleteMissingId() {
        when(repository.deleteById(100000)).thenReturn(false);
        assertFalse(service.deleteById(100000));
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    private Member makeMember() {
        Member member = new Member();
        member.setMemberId(0);
        member.setIsAdmin(0);

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

        return member;
    }
}