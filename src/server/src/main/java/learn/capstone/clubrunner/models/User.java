package learn.capstone.clubrunner.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    private List<Runner> runsParticipating = new ArrayList<>();
    private List<Member> memberships = new ArrayList<>();
    private List<Run> runsCreated = new ArrayList<>();

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Runner> getRunsParticipating() {
        return runsParticipating;
    }

    public void setRunsParticipating(List<Runner> runsParticipating) {
        this.runsParticipating = runsParticipating;
    }

    public List<Member> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Member> memberships) {
        this.memberships = memberships;
    }

    public List<Run> getRunsCreated() {
        return runsCreated;
    }

    public void setRunsCreated(List<Run> runsCreated) {
        this.runsCreated = runsCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_id == user.user_id && first_name.equals(user.first_name) && last_name.equals(user.last_name) && email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, first_name, last_name, email, password);
    }
}
