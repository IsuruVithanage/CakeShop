package view.tm;

import javafx.scene.control.Button;

public class UserTM {
    private String workerID;
    private String username;
    private String roll;
    private String password;
    private Button btn;

    public UserTM() {
    }

    public UserTM(String workerID, String username, String roll, String password, Button btn) {
        this.workerID = workerID;
        this.username = username;
        this.roll = roll;
        this.password = password;
        this.btn = btn;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "workerID='" + workerID + '\'' +
                ", username='" + username + '\'' +
                ", roll='" + roll + '\'' +
                ", password='" + password + '\'' +
                ", btn=" + btn +
                '}';
    }
}
