package learn.capstone.clubrunner.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Club {
    private int clubId;
    private String name;
    private String description;

    private List<Member> members = new ArrayList<>();
    private List<Run> runsHosted = new ArrayList<>();

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Run> getRunsHosted() {
        return runsHosted;
    }

    public void setRunsHosted(List<Run> runsHosted) {
        this.runsHosted = runsHosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return clubId == club.clubId && Objects.equals(name, club.name) && Objects.equals(description, club.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clubId, name, description);
    }
}
