package learn.capstone.clubrunner.models;

import java.util.Objects;

public class RunStatus {
    private int runStatusId;
    private String status;

    public int getRunStatusId() {
        return runStatusId;
    }

    public void setRunStatusId(int runStatusId) {
        this.runStatusId = runStatusId;
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
        return runStatusId == runStatus.runStatusId && status.equals(runStatus.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runStatusId, status);
    }

    @Override
    public String toString() {
        return "RunStatus{" +
                "runStatusId=" + runStatusId +
                ", status='" + status + '\'' +
                '}';
    }
}
