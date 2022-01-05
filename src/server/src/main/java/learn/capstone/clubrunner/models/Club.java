package learn.capstone.clubrunner.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Club {
    private int club_id;
    private String name;
    private String description;

    private List<Member> members = new ArrayList<>();
    private List<Run> runsHosted = new ArrayList<>();

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
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
        return club_id == club.club_id && name.equals(club.name) && Objects.equals(description, club.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(club_id, name, description);
    }
}
