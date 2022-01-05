package learn.capstone.clubrunner.models;

import java.util.Objects;

public class Runner {
    private int runner_id;

    private User user;
    private Run run;

    public int getRunner_id() {
        return runner_id;
    }

    public void setRunner_id(int runner_id) {
        this.runner_id = runner_id;
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
        return runner_id == runner.runner_id && Objects.equals(user, runner.user) && Objects.equals(run, runner.run);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runner_id, user, run);
    }
}
