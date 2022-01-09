package learn.capstone.clubrunner.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Run {
    private int runId;
    private LocalDate date;
    private String address;
    private String description;
    private int maxCapacity;
    private LocalTime startTime;
    private BigDecimal latitude;
    private BigDecimal longitude;

    private Club club;
    private User user;
    private RunStatus runStatus;

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RunStatus getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(RunStatus runStatus) {
        this.runStatus = runStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Run run = (Run) o;
        return runId == run.runId && maxCapacity == run.maxCapacity && date.equals(run.date) && address.equals(run.address) && Objects.equals(description, run.description) && startTime.equals(run.startTime) && latitude.equals(run.latitude) && longitude.equals(run.longitude) && club.equals(run.club) && user.equals(run.user) && runStatus.equals(run.runStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runId, date, address, description, maxCapacity, startTime, latitude, longitude, club, user, runStatus);
    }
}
