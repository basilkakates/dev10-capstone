package learn.capstone.clubrunner.models;

import java.util.Objects;

public class Member {
    private int memberId;
    private int isAdmin;

    private User user;
    private Club club;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberId == member.memberId && isAdmin == member.isAdmin && user.equals(member.user) && club.equals(member.club);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, isAdmin, user, club);
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", isAdmin=" + isAdmin +
                ", user=" + user +
                ", club=" + club +
                '}';
    }
}
