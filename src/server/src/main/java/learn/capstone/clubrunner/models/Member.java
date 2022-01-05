package learn.capstone.clubrunner.models;

import java.util.Objects;

public class Member {
    private int member_id;
    private int isAdmin;

    private User user;
    private Club club;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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
        return member_id == member.member_id && isAdmin == member.isAdmin && Objects.equals(user, member.user) && Objects.equals(club, member.club);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member_id, isAdmin, user, club);
    }
}
