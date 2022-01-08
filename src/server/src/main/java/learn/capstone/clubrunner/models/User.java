package learn.capstone.clubrunner.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private List<Runner> runsParticipating = new ArrayList<>();
    private List<Member> memberships = new ArrayList<>();
    private List<Run> runsCreated = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return userId == user.userId && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, password);
    }
}
