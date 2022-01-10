package learn.capstone.clubrunner.models;

import java.util.Objects;

public class Runner {
    private int runnerId;

    private User user;
    private Run run;

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return runnerId == runner.runnerId && user.equals(runner.user) && run.equals(runner.run);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runnerId, user, run);
    }

    @Override
    public String toString() {
        return "Runner{" +
                "runnerId=" + runnerId +
                ", user=" + user +
                ", run=" + run +
                '}';
    }
}
