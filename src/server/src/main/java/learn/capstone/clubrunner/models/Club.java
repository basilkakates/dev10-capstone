package learn.capstone.clubrunner.models;

public class Club {

    private int club_id;
    private String name;
    private String description;

    //Do I need to add an arraylist for matching bridge tables? like in fieldagent?

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
}
