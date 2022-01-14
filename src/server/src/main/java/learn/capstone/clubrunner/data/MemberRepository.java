package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Member;

import java.util.List;

public interface MemberRepository {
    Member findById(int memberId);

    List<Member> findByUserId(int userId);

    List<Member> findByClubId(int clubId);

    List<Member> findAdmins();

    Member findAdminsByUserId(int userId);

    List<Member> findAdminsByClubId(int clubId);

    List<Member> findAll();

    Member add(Member member);

    boolean update(Member member);

    boolean deleteById(int memberId);
}
