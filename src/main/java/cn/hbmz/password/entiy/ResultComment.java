package cn.hbmz.password.entiy;

public class ResultComment {
    private int state;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
