package controller;

import com.jfoenix.controls.JFXButton;
import controller.contollerUtil.WorkerControllerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import model.Worker;
import util.DisplayTimeDate;
import view.tm.WorkerTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class WorkerDetailController extends WorkerControllerUtil {
    public Label lbTime;
    public Label lbDate;
    public Label lbWorkerID;
    public TextField txtWorkerName;
    public TextField txtNIC;
    public TextField txtContact;
    public TableColumn<WorkerTM, String> colID;
    public TableColumn<WorkerTM, String> colName;
    public TableColumn<WorkerTM, String> colContact;
    public TableColumn<WorkerTM, String> colNIC;
    public TableColumn<WorkerTM, String> colAddress;
    public TableColumn<WorkerTM, String> colBank;
    public TableColumn<WorkerTM, String> colAvailabilyty;
    public TableColumn<WorkerTM, String> colRemove;
    public TextField txtBank;
    public TextField txtAddress;
    public TableView<WorkerTM> tblWorker;
    public JFXButton addbtn;
    public JFXButton updatebtn;
    public Label lbname;
    public Label lbnic;
    public Label lbcontact;
    public Label lbbank;
    public Label lbaddress;
    ObservableList<WorkerTM> obList = FXCollections.observableArrayList();

    public void initialize() {

        colID.setCellValueFactory(new PropertyValueFactory<>("workerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("workerName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("workerAddress"));
        colBank.setCellValueFactory(new PropertyValueFactory<>("bankACNo"));
        colAvailabilyty.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));

        DisplayTimeDate.loadDateAndTime(lbDate, lbTime);

        setWorkerID();

        updatebtn.setDisable(true);

        txtWorkerName.setOnAction(event -> {
            validate(KeyCode.ENTER, txtContact, "^[A-z ]{3,20}$", txtWorkerName, lbname);
        });

        txtContact.setOnAction(event -> {
            validate(KeyCode.ENTER, txtNIC, "^[0-9]*$", txtContact, lbcontact);
        });

        txtNIC.setOnAction(event -> {
            validate(KeyCode.ENTER, txtBank, "^[1-9][0-9]*[A-z]?$", txtNIC, lbnic);
        });

        txtBank.setOnAction(event -> {
            validate(KeyCode.ENTER, txtAddress, "^[1-9][0-9]*([.][0-9]{2})?$", txtBank, lbbank);
        });

        try {
            loadWorkerDetail(getAllWorkers());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //Table select
        tblWorker.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatebtn.setDisable(false);
            addbtn.setDisable(true);
            try {
                setWorkerData(obList.get((Integer) newValue).getWorkerID());
            } catch (Exception e) {
                //new Alert(Alert.AlertType.WARNING, "Error").show();
            }
        });

    }

    //Validate textfields
    public void validate(KeyCode keyCode, TextField txt, String regex, TextField txtNow, Label label) {
        if (keyCode == KeyCode.ENTER) {
            if (!txtNow.getText().matches(regex)) {
                label.setStyle("-fx-text-fill: red");
                txtNow.setStyle("-fx-border-color: red");
                txtNow.setText("");
            } else {
                label.setStyle("-fx-text-fill: #ff7197");
                txtNow.setStyle("-fx-border-color: #ff7197");
                txt.requestFocus();
            }
        }

    }


    public void addWorker(MouseEvent mouseEvent) {

        if (!txtWorkerName.getText().trim().isEmpty() && !txtContact.getText().trim().isEmpty() && !txtNIC.getText().trim().isEmpty() && !txtBank.getText().trim().isEmpty() && !txtAddress.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: #ff7197");
            txtWorkerName.setStyle("-fx-border-color:  #ff7197");
            lbcontact.setStyle("-fx-text-fill: #ff7197");
            txtContact.setStyle("-fx-border-color:  #ff7197");
            lbnic.setStyle("-fx-text-fill: #ff7197");
            txtNIC.setStyle("-fx-border-color:  #ff7197");
            lbbank.setStyle("-fx-text-fill: #ff7197");
            txtBank.setStyle("-fx-border-color:  #ff7197");
            lbaddress.setStyle("-fx-text-fill: #ff7197");
            txtAddress.setStyle("-fx-border-color:  #ff7197");

            Worker worker = new Worker(
                    lbWorkerID.getText(),
                    txtWorkerName.getText(),
                    txtContact.getText(),
                    txtNIC.getText(),
                    txtAddress.getText(),
                    txtBank.getText(),
                    "Yes"
            );

            saveWorker(worker);
            clearField();
        } else if (txtWorkerName.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: red");
            txtWorkerName.setStyle("-fx-border-color: red");
        } else if (txtContact.getText().trim().isEmpty()) {
            lbcontact.setStyle("-fx-text-fill: red");
            txtContact.setStyle("-fx-border-color: red");
        } else if (txtNIC.getText().trim().isEmpty()) {
            lbnic.setStyle("-fx-text-fill: red");
            txtNIC.setStyle("-fx-border-color: red");
        } else if (txtBank.getText().trim().isEmpty()) {
            lbbank.setStyle("-fx-text-fill: red");
            txtBank.setStyle("-fx-border-color: red");
        } else {
            lbaddress.setStyle("-fx-text-fill: red");
            txtAddress.setStyle("-fx-border-color: red");
        }
    }

    private void saveWorker(Worker worker) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                if (saveWorkers(worker)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    tblWorker.getItems().clear();

                    try {
                        loadWorkerDetail(getAllWorkers());
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                    setWorkerID();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Duplicate Entry..").show();
            }
        }
    }

    private void setWorkerID() {
        try {
            lbWorkerID.setText(creatWorkerID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadWorkerDetail(ArrayList<Worker> allWorkers) {
        for (Worker worker : allWorkers) {
            Button btn = new Button("Remove");
            WorkerTM tm = new WorkerTM(
                    worker.getWorkerID(),
                    worker.getWorkerName(),
                    worker.getContact(),
                    worker.getNIC(),
                    worker.getWorkerAddress(),
                    worker.getBankACNo(),
                    worker.getAvailability(),
                    btn
            );
            obList.add(tm);
            removeWorker(btn, tm);
        }
        tblWorker.setItems(obList);
    }

    private void removeWorker(Button btn, WorkerTM tm) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    deleteWorker(tm.getWorkerID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(tm);
                tblWorker.refresh();
                setWorkerID();
            }
        });
    }

    public void updateWorker(MouseEvent mouseEvent) {

        if (!txtWorkerName.getText().trim().isEmpty() && !txtContact.getText().trim().isEmpty() && !txtNIC.getText().trim().isEmpty() && !txtBank.getText().trim().isEmpty() && !txtAddress.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: #ff7197");
            txtWorkerName.setStyle("-fx-border-color:  #ff7197");
            lbcontact.setStyle("-fx-text-fill: #ff7197");
            txtContact.setStyle("-fx-border-color:  #ff7197");
            lbnic.setStyle("-fx-text-fill: #ff7197");
            txtNIC.setStyle("-fx-border-color:  #ff7197");
            lbbank.setStyle("-fx-text-fill: #ff7197");
            txtBank.setStyle("-fx-border-color:  #ff7197");
            lbaddress.setStyle("-fx-text-fill: #ff7197");
            txtAddress.setStyle("-fx-border-color:  #ff7197");

            Worker worker = new Worker(
                    lbWorkerID.getText(),
                    txtWorkerName.getText(),
                    txtContact.getText(),
                    txtNIC.getText(),
                    txtAddress.getText(),
                    txtBank.getText(),
                    "Yes"
            );

            Button btn = new Button("Remove");
            WorkerTM tm = new WorkerTM(
                    worker.getWorkerID(),
                    worker.getWorkerName(),
                    worker.getContact(),
                    worker.getNIC(),
                    worker.getWorkerAddress(),
                    worker.getBankACNo(),
                    worker.getAvailability(),
                    btn
            );
            removeWorker(btn, tm);

            int rownumber = isExists(tm, obList);
            if (rownumber == -1) {
                new Alert(Alert.AlertType.WARNING, "No record found..").show();
            } else {
                try {
                    deleteWorker(tm.getWorkerID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(rownumber);
                saveWorker(worker);

            }
            clearField();
            setWorkerID();
            addbtn.setDisable(false);
            updatebtn.setDisable(true);

        } else if (txtWorkerName.getText().trim().isEmpty()) {
            lbname.setStyle("-fx-text-fill: red");
            txtWorkerName.setStyle("-fx-border-color: red");
        } else if (txtContact.getText().trim().isEmpty()) {
            lbcontact.setStyle("-fx-text-fill: red");
            txtContact.setStyle("-fx-border-color: red");
        } else if (txtNIC.getText().trim().isEmpty()) {
            lbnic.setStyle("-fx-text-fill: red");
            txtNIC.setStyle("-fx-border-color: red");
        } else if (txtBank.getText().trim().isEmpty()) {
            lbbank.setStyle("-fx-text-fill: red");
            txtBank.setStyle("-fx-border-color: red");
        } else {
            lbaddress.setStyle("-fx-text-fill: red");
            txtAddress.setStyle("-fx-border-color: red");
        }

    }

    private int isExists(WorkerTM tm, ObservableList<WorkerTM> obList) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getWorkerID().equals(obList.get(i).getWorkerID())) {
                return i;
            }
        }
        return -1;
    }

    public void setWorkerData(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Worker> workerArrayList = getAllWorkers();

        for (Worker worker : workerArrayList) {
            if (id.equals(worker.getWorkerID())) {
                lbWorkerID.setText(worker.getWorkerID());
                txtWorkerName.setText(worker.getWorkerName());
                txtContact.setText(worker.getContact());
                txtNIC.setText(worker.getNIC());
                txtBank.setText(worker.getBankACNo());
                txtAddress.setText(worker.getWorkerAddress());
                break;
            }
        }
    }

    private void clearField() {
        txtWorkerName.clear();
        txtContact.clear();
        txtNIC.clear();
        txtBank.clear();
        txtAddress.clear();
    }
}
