package learn.capstone.clubrunner.models;

import java.util.Objects;

public class RunStatus {

    private int run_status_id;
    private String status;

    //Do I need to add an arraylist for matching bridge tables? like in fieldagent?

    public int getRun_status_id() {
        return run_status_id;
    }

    public void setRun_status_id(int run_status_id) {
        this.run_status_id = run_status_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunStatus runStatus = (RunStatus) o;
        return run_status_id == runStatus.run_status_id && status.equals(runStatus.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(run_status_id, status);
    }
}
