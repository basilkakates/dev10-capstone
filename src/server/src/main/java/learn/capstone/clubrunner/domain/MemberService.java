package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.ClubRepository;
import learn.capstone.clubrunner.data.MemberRepository;
import learn.capstone.clubrunner.data.UserRepository;
import learn.capstone.clubrunner.models.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    public MemberService(MemberRepository memberRepository, UserRepository userRepository, ClubRepository clubRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
    }

    public Result<Member> findById(int memberId) {
        Result<Member> result = new Result<>();
        result.setPayload(memberRepository.findById(memberId));

        if (result.getPayload() == null) {
            String msg = String.format("memberId: %s not found", memberId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public List<Member> findByUserId(int userId) {
        return memberRepository.findByUserId(userId);
    }

    public List<Member> findByClubId(int clubId) {
        return memberRepository.findByClubId(clubId);
    }

    public List<Member> findAdmins() {
        return memberRepository.findAdmins();
    }

    public Result<Member> findAdminsByUserId(int userId) {
        Result<Member> result = new Result<>();
        result.setPayload(memberRepository.findAdminsByUserId(userId));

        if (result.getPayload() == null) {
            String msg = String.format("userId %s is not an Admin for any club", userId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public List<Member> findAdminsByClubId(int clubId) {
        return memberRepository.findAdminsByClubId(clubId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Result<Member> add(Member member) {
        Result<Member> result = validate(member);

        if (!result.isSuccess()) {
            return result;
        }

        if (member.getMemberId() != 0) {
            result.addMessage("memberId cannot be set for `add` operation", ResultType.INVALID);
        } else if (memberRepository.findByUserId(member.getUser().getUserId()).stream()
                .anyMatch(memberByUser -> memberByUser.getClub().getClubId() == member.getClub().getClubId())) {
            String msg = String.format("userId: %s is already a member of clubId: %s", member.getUser().getUserId(), member.getClub().getClubId());
            result.addMessage(msg, ResultType.INVALID);
        } else if (member.getIsAdmin() == 1 && memberRepository.findByUserId(member.getUser().getUserId()).stream()
                .anyMatch(memberByUser -> memberByUser.getIsAdmin() == 1)) {
            String msg = String.format("userId: %s is already an admin of another club", member.getUser().getUserId());
            result.addMessage(msg, ResultType.INVALID);
        } else {
            result.setPayload(memberRepository.add(member));
        }

        return result;
    }

    public Result<Member> update(Member member) {
        Result<Member> result = validate(member);

        if (!result.isSuccess()) {
            return result;
        }

        if (member.getMemberId() <= 0) {
            result.addMessage("memberId must be set for `update` operation", ResultType.INVALID);
        } else if (memberRepository.findByUserId(member.getUser().getUserId()).stream()
                .anyMatch(memberByUser -> memberByUser.getClub().getClubId() == member.getClub().getClubId() && memberByUser.getMemberId() != member.getMemberId())) {
            String msg = String.format("userId: %s is already a member of clubId: %s", member.getUser().getUserId(), member.getClub().getClubId());
            result.addMessage(msg, ResultType.INVALID);
        } else if (member.getIsAdmin() == 1 && memberRepository.findByUserId(member.getUser().getUserId()).stream()
                .anyMatch(memberByUser -> memberByUser.getIsAdmin() == 1 && memberByUser.getMemberId() != member.getMemberId())) {
            String msg = String.format("userId: %s is already an admin of another club", member.getUser().getUserId());
            result.addMessage(msg, ResultType.INVALID);
        }

        if (result.isSuccess() && !memberRepository.update(member)) {
            String msg = String.format("memberId: %s not found", member.getMemberId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int memberId) {
        return memberRepository.deleteById(memberId);
    }

    private Result<Member> validate(Member member) {
        Result<Member> result = new Result<>();

        if (member == null) {
            result.addMessage("member cannot be null", ResultType.INVALID);
            return result;
        }

        if (member.getUser() == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
        } else if (userRepository.findById(member.getUser().getUserId()) == null) {
            String msg = String.format("userId %s not found", member.getUser().getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        if (member.getClub() == null) {
            result.addMessage("club cannot be null", ResultType.INVALID);
        } else if (clubRepository.findById(member.getClub().getClubId()) == null) {
            String msg = String.format("clubId %s not found", member.getClub().getClubId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }
}
