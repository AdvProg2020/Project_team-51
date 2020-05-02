package model;

public class Status {
    private StatusStates state ;


    public StatusStates getState() {
        return state;
    }

    public void setState(StatusStates state) {
        this.state = state;
    }

    public Status(StatusStates state) {
        this.state = state;
    }


}
