package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controller.contollerUtil.SalaryControllerUtill;
import controller.contollerUtil.WorkerControllerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import model.Worker;
import model.WorkerSalary;
import util.DisplayTimeDate;
import view.tm.SalaryTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class SalaryWindowController extends SalaryControllerUtill {
    public Label lbTime;
    public Label lbDate;
    public TextField txtDayperMonth;
    public TextField txtPerDaySalary;
    public TableView<SalaryTM> tblSalary;
    public TableColumn<SalaryTM, String> colWorkerID;
    public TableColumn<SalaryTM, String> colDayPerMonth;
    public TableColumn<SalaryTM, String> colPerSalary;
    public TableColumn<SalaryTM, String> colSalary;
    public TableColumn<SalaryTM, String> colPay;
    public JFXButton addBtn;
    public JFXComboBox<String> cmbWorkerID;
    public Label lbSalary;
    public JFXButton updatebtn;
    ArrayList<Worker> workerArrayList;
    ObservableList<SalaryTM> obList = FXCollections.observableArrayList();

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

        try {
            loadSalaryDetail(getAllSalary());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        updatebtn.setDisable(true);

        colWorkerID.setCellValueFactory(new PropertyValueFactory<>("workerID"));
        colDayPerMonth.setCellValueFactory(new PropertyValueFactory<>("workPerMonth"));
        colPerSalary.setCellValueFactory(new PropertyValueFactory<>("salaryPerDay"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colPay.setCellValueFactory(new PropertyValueFactory<>("pay"));

        txtPerDaySalary.setOnAction(event -> {
            validate(KeyCode.ENTER, txtPerDaySalary, "^[1-9][0-9]*([.][0-9]{2})?$", txtPerDaySalary, lbSalary);
        });

        //Select Worker
        cmbWorkerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setWoredDays(String.valueOf(newValue));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        //Table select
        tblSalary.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatebtn.setDisable(false);
            addBtn.setDisable(true);
            try {
                setSalaryData(obList.get((Integer) newValue).getWorkerID());
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

    //Set the salary data in to text Fields
    private void setSalaryData(String workerID) throws SQLException, ClassNotFoundException {
        ArrayList<WorkerSalary> salaries = getAllSalary();

        for (WorkerSalary workerSalary : salaries) {
            if (workerID.equals(workerSalary.getWorkerID())) {
                cmbWorkerID.setValue(workerID);
                txtDayperMonth.setText(String.valueOf(workerSalary.getWorkPerMonth()));
                txtPerDaySalary.setText(String.valueOf(workerSalary.getSalaryPerDay()));
                break;
            }
        }
    }

    //Set the total days that worked
    private void setWoredDays(String workerID) throws SQLException, ClassNotFoundException {
        LocalDate currentMonth = LocalDate.now();
        Month month = currentMonth.getMonth();

        int days = getWorkDays(workerID, String.valueOf(month));
        txtDayperMonth.setText(String.valueOf(days));
    }

    //Load All Workers in to comboBox
    public void loadWorker() {

        ArrayList<String> workerID = new ArrayList<>();

        for (Worker worker : workerArrayList) {
            workerID.add(worker.getWorkerID());
        }

        cmbWorkerID.getItems().addAll(workerID);

    }

    //Add Salary
    public void addSalary(MouseEvent mouseEvent) {

        if (!txtPerDaySalary.getText().trim().isEmpty() && !cmbWorkerID.getSelectionModel().isEmpty() && !checkSalary(cmbWorkerID.getSelectionModel().getSelectedItem())) {
            lbSalary.setStyle("-fx-text-fill: #ff7197");
            txtPerDaySalary.setStyle("-fx-border-color:  #ff7197");

            WorkerSalary workerSalary = new WorkerSalary(
                    lbDate.getText(),
                    cmbWorkerID.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(txtDayperMonth.getText()),
                    Double.parseDouble(txtPerDaySalary.getText()),
                    Double.parseDouble(txtPerDaySalary.getText()) * Double.parseDouble(txtDayperMonth.getText()),
                    "Not Payed"

            );

            saveWorkerSalary(workerSalary);

        } else if (txtPerDaySalary.getText().trim().isEmpty()) {
            lbSalary.setStyle("-fx-text-fill: red");
            txtPerDaySalary.setStyle("-fx-border-color: red");
        } else if (checkSalary(cmbWorkerID.getSelectionModel().getSelectedItem())) {
            new Alert(Alert.AlertType.WARNING, "This Worker had been payed for this month").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Error").show();
        }

    }

    private boolean checkSalary(String id) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String day = String.format("%02d", localDate.getYear()) + "-" + String.format("%02d", localDate.getMonthValue()) + "-";
        try {
            ArrayList<WorkerSalary> workerSalaries = getAllSalary();
            for (WorkerSalary workerSalary : workerSalaries) {
                if (workerSalary.getWorkerID().equals(id) && workerSalary.getDate().contains(day)) {
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //Load All Salary details in to table
    public void loadSalaryDetail(ArrayList<WorkerSalary> workerSalaries) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String day = String.format("%02d", localDate.getYear()) + "-" + String.format("%02d", localDate.getMonthValue()) + "-";


        for (WorkerSalary workerSalary : workerSalaries) {
            if (workerSalary.getState().equals("Not Payed") && workerSalary.getDate().contains(day)) {
                Button btn = new Button("Pay");
                SalaryTM tm = new SalaryTM(
                        workerSalary.getWorkerID(),
                        workerSalary.getWorkPerMonth(),
                        workerSalary.getSalaryPerDay(),
                        workerSalary.getSalary(),
                        btn
                );
                obList.add(tm);
                pay(btn, tm);
            }
        }
        tblSalary.setItems(obList);
    }

    //Save salary in the database
    private void saveWorkerSalary(WorkerSalary workerSalary) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                if (saveSalary(workerSalary)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    tblSalary.getItems().clear();

                    loadSalaryDetail(getAllSalary());

                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Duplicate Entry..").show();
            }
        }
    }

    //Update salary
    public void updateSalary(MouseEvent mouseEvent) {

        if (!txtPerDaySalary.getText().trim().isEmpty() && !cmbWorkerID.getSelectionModel().isEmpty()) {
            lbSalary.setStyle("-fx-text-fill: #ff7197");
            txtPerDaySalary.setStyle("-fx-border-color:  #ff7197");

            WorkerSalary workerSalary = new WorkerSalary(
                    lbDate.getText(),
                    cmbWorkerID.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(txtDayperMonth.getText()),
                    Double.parseDouble(txtPerDaySalary.getText()),
                    Double.parseDouble(txtPerDaySalary.getText()) * Double.parseDouble(txtDayperMonth.getText()),
                    "Not Payed"

            );

            Button btn = new Button("Pay");
            SalaryTM tm = new SalaryTM(
                    cmbWorkerID.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(txtDayperMonth.getText()),
                    Double.parseDouble(txtPerDaySalary.getText()),
                    Double.parseDouble(txtPerDaySalary.getText()) * Double.parseDouble(txtDayperMonth.getText()),
                    btn
            );
            pay(btn, tm);

            int rownumber = isExists(tm, obList);
            if (rownumber == -1) {
                new Alert(Alert.AlertType.WARNING, "No record found..").show();
            } else {
                try {
                    deleteSalary(tm.getWorkerID());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(rownumber);

                saveWorkerSalary(workerSalary);
            }

        } else {
            lbSalary.setStyle("-fx-text-fill: red");
            txtPerDaySalary.setStyle("-fx-border-color: red");
        }

    }

    //Check the oblist
    private int isExists(SalaryTM tm, ObservableList<SalaryTM> obList) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getWorkerID().equals(obList.get(i).getWorkerID())) {
                return i;
            }
        }
        return -1;
    }

    //Pay the worker
    private void pay(Button btn, SalaryTM tm) {
        btn.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    paySalary(tm.getWorkerID(), lbDate.getText());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                obList.remove(tm);
            }
        });
    }
}
