package learn.capstone.clubrunner.models;

import java.util.ArrayList;
import java.util.List;

public class Club {

    private int club_id;
    private String name;
    private String description;
    private List<Member> members = new ArrayList<>();
    private List<Run> runs = new ArrayList<>();

    //Do I need to add an arraylist for matching bridge tables? like in fieldagent?
    //club will have list of members
    //club will have a list of run

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

    public List<Run> getRuns() {
        return runs;
    }

    public void setRuns(List<Run> runs) {
        this.runs = runs;
    }
}
