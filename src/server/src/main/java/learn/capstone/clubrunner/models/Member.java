package learn.capstone.clubrunner.models;

public class Member {

    private int member_id;
    private int user_id;
    private int club_id;
    private int isAdmin;

    //Do I need to add an arraylist for matching bridge tables? like in fieldagent?

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public int isAdmin() {
        return isAdmin;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }
}
