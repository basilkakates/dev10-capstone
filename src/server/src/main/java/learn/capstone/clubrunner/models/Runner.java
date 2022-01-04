package learn.capstone.clubrunner.models;

public class Runner {

    private int runner_id;
    private int run_id;
    private int user_id;

    //Do I need to add an arraylist for matching bridge tables? like in fieldagent?

    public int getRunner_id() {
        return runner_id;
    }

    public void setRunner_id(int runner_id) {
        this.runner_id = runner_id;
    }

    public int getRun_id() {
        return run_id;
    }

    public void setRun_id(int run_id) {
        this.run_id = run_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
