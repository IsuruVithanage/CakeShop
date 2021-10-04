package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controller.contollerUtil.UserUtil;
import controller.contollerUtil.WorkerControllerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.User;
import model.Worker;
import util.DisplayTimeDate;
import view.tm.UserTM;

import java.sql.SQLException;
import java.util.*;

public class UserDetailsController {
    public Label lbTime;
    public Label lbDate;
    public TextField txtUserName;
    public TextField txtPassword;
    public TableView<UserTM> tblUser;
    public TableColumn<UserTM, String> colUserName;
    public TableColumn<UserTM, String> colRoll;
    public TableColumn<UserTM, String> colPassword;
    public TableColumn<UserTM, String> colRemove;
    public JFXButton addbtn;
    public JFXComboBox<String> cmbWorkerID;
    public JFXComboBox<String> cmbRoll;
    public TableColumn<UserTM, String> colWorkerID;
    ArrayList<Worker> workerArrayList;
    ObservableList<UserTM> obList = FXCollections.observableArrayList();

    {
        try {
            workerArrayList = new WorkerControllerUtil().getAllWorkers();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize() {
        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);
        loadWorker();
        loadRoll();
        try {
            loadUser(new UserUtil().getAllUsers());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        colWorkerID.setCellValueFactory(new PropertyValueFactory<>("workerID"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRoll.setCellValueFactory(new PropertyValueFactory<>("roll"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));


        //Select Worker
        cmbWorkerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setUsername(String.valueOf(newValue));
            setPassword(String.valueOf(newValue));
        });

        //Select roll
        cmbRoll.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setPassword(String.valueOf(newValue));
        });

    }

    //Load All Workers in to comboBox
    public void loadWorker() {

        ArrayList<String> workerID = new ArrayList<>();

        for (Worker worker : workerArrayList) {
            workerID.add(worker.getWorkerID());
        }

        cmbWorkerID.getItems().addAll(workerID);

    }

    //Load roll
    public void loadRoll() {
        List<String> weightList = Arrays.asList("Admin", "Worker");
        cmbRoll.getItems().addAll(weightList);
    }

    //load Username
    public void setUsername(String id) {
        String username = null;


        for (Worker worker : workerArrayList) {
            if (worker.getWorkerID().equals(id)) {
                String name = worker.getWorkerName().split(" ")[0];
                username = worker.getWorkerID() + name;
            }
        }
        txtUserName.setText(username);
    }

    //load Password
    public void setPassword(String roll) {
        String password = null;
        int num = new Random().hashCode();

        for (Worker worker : workerArrayList) {
            if (worker.getWorkerID().equals(cmbWorkerID.getSelectionModel().getSelectedItem())) {
                String name = worker.getWorkerName().split(" ")[0];
                password = name + num + roll.toUpperCase();
            }
        }
        txtPassword.setText(password);
    }


    public void addUser(MouseEvent mouseEvent) {
        User user = new User(
                cmbWorkerID.getSelectionModel().getSelectedItem(),
                txtUserName.getText(),
                txtPassword.getText()

        );

        saveUser(user);

    }

    private void saveUser(User user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                if (new UserUtil().saveUser(user)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    tblUser.getItems().clear();

                    try {
                        loadUser(new UserUtil().getAllUsers());
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Duplicate Entry..").show();
            }
        }
    }

    private void loadUser(ArrayList<User> allUsers) {

        for (User user : allUsers) {
            String roll = null;
            if (user.getPassword().contains("ADMIN")) {
                roll = "Admin";
            } else {
                roll = "Worker";
            }

            Button btn = new Button("Remove");
            UserTM tm = new UserTM(
                    user.getWorkerID(),
                    user.getUsername(),
                    roll,
                    user.getPassword(),
                    btn
            );
            obList.add(tm);
            removeUser(btn, tm);
        }
        tblUser.setItems(obList);
    }


    //Remove a User from the table
    public void removeUser(Button btn, UserTM tm) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    new UserUtil().deleteUser(tm.getWorkerID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(tm);
                tblUser.refresh();
            }
        });
    }
}
